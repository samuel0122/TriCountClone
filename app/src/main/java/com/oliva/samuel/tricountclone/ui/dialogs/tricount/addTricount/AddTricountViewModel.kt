package com.oliva.samuel.tricountclone.ui.dialogs.tricount.addTricount

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.domain.AddParticipantUseCase
import com.oliva.samuel.tricountclone.domain.AddTricountUseCase
import com.oliva.samuel.tricountclone.domain.GetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTricountViewModel @Inject constructor(
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val addTricountUseCase: AddTricountUseCase,
    private val addParticipantUseCase: AddParticipantUseCase
) : ViewModel() {
    private val _tricountModel = mutableStateOf(TricountModel.default())
    val tricountModel: State<TricountModel>
        get() = _tricountModel

    private val _userParticipant = mutableStateOf(ParticipantModel.default())
    val userParticipant: State<ParticipantModel>
        get() = _userParticipant

    private val _participantsList = mutableStateListOf<ParticipantModel>()
    val participantsList: List<ParticipantModel>
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

                _userParticipant.value = loggedUserParticipant
            }
        }
    }

    fun onTricountModelChanged(tricountModel: TricountModel) {
        _tricountModel.value = tricountModel
    }

    fun onParticipantModelChanged(participantModel: ParticipantModel) {
        participantsList.find { it.id == participantModel.id }?.let {
            _participantsList[participantsList.indexOf(it)] = participantModel
        } ?: run {
            if (userParticipant.value.id == participantModel.id) {
                _userParticipant.value = participantModel
            }
        }
    }

    fun onAddParticipant() {
        if (participantsList.any { it.name.isEmpty() }) return

        _participantsList.add(ParticipantModel.default(tricountModel.value.id))
    }

    fun onRemoveParticipant(participantModel: ParticipantModel) {
        _participantsList.removeIf { it.id == participantModel.id }
    }

    fun onSubmit(
        onSubmitted: (TricountModel) -> Unit
    ) {
        viewModelScope.launch {
            addTricountUseCase(
                tricountModel = tricountModel.value
            )

            addParticipantUseCase(userParticipant.value)

            participantsList
                .filter { it.name.isNotEmpty() }
                .forEach { addParticipantUseCase(it) }

            onSubmitted(tricountModel.value)
        }
    }
}
