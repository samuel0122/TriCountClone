package com.oliva.samuel.tricountclone.ui.dialogs.tricount.addTricount

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.domain.GetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTricountViewModel @Inject constructor(
    private val getLoggedUserUseCase: GetLoggedUserUseCase
) : ViewModel() {

    private val _tricountModel = mutableStateOf(TricountModel.default().toUiModel())
    val tricountModel: State<TricountUiModel>
        get() = _tricountModel

    private val _userParticipant = mutableStateOf(ParticipantModel.default().toUiModel())
    val userParticipant: State<ParticipantUiModel>
        get() = _userParticipant

    private val _participantsList = mutableStateListOf<ParticipantUiModel>()
    val participantsList: List<ParticipantUiModel>
        get() = _participantsList

    init {
        viewModelScope.launch {
            getLoggedUserUseCase()?.let { loggerUser ->
                val loggedUserParticipant = ParticipantModel
                    .default(tricountModel.value.id)
                    .copy(
                        name = loggerUser.name,
                        userId = loggerUser.id
                    )

                _userParticipant.value = loggedUserParticipant.toUiModel()
            }
        }
    }

    fun onTricountModelChanged(tricount: TricountUiModel) {
        _tricountModel.value = tricount
    }

    fun onParticipantModelChanged(participant: ParticipantUiModel) {
        participantsList.find { it.id == participant.id }?.let {
            _participantsList[participantsList.indexOf(it)] = participant
        } ?: run {
            if (userParticipant.value.id == participant.id) {
                _userParticipant.value = participant
            }
        }
    }

    fun onAddParticipant() {
        if (participantsList.any { it.name.isEmpty() }) return

        _participantsList.add(ParticipantModel.default(tricountModel.value.id).toUiModel())
    }

    fun onRemoveParticipant(participant: ParticipantUiModel) {
        _participantsList.removeIf { it.id == participant.id }
    }

    fun onSubmit(
        onSubmitted: (TricountUiModel, List<ParticipantUiModel>) -> Unit
    ) {
        viewModelScope.launch {
            val tricountParticipants = participantsList
                .filter { it.name.isNotEmpty() }
                .plus(userParticipant.value)

            onSubmitted(tricountModel.value, tricountParticipants)
        }
    }
}
