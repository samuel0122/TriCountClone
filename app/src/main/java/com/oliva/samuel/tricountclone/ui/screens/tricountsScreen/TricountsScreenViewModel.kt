package com.oliva.samuel.tricountclone.ui.screens.tricountsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.domain.AddParticipantUseCase
import com.oliva.samuel.tricountclone.domain.AddTricountUseCase
import com.oliva.samuel.tricountclone.domain.GetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.GetTricountsUseCase
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel
import com.oliva.samuel.tricountclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TricountsScreenViewModel @Inject constructor(
    private val getTricountsUseCase: GetTricountsUseCase,
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val addTricountUseCase: AddTricountUseCase,
    private val addParticipantUseCase: AddParticipantUseCase
) : ViewModel() {

    private val _tricountsList = MutableStateFlow<Resource<List<TricountUiModel>>>(Resource.Loading)
    val tricountsList: StateFlow<Resource<List<TricountUiModel>>> = _tricountsList

    private val _showAddTricountDialog = MutableLiveData<Boolean>()
    val showAddTricountDialog: LiveData<Boolean> = _showAddTricountDialog

    init {
        viewModelScope.launch {
            getTricountsUseCase()
                .map { Resource.Success(it) }
                .catch { error ->
                    _tricountsList.value = Resource.Error(error, error.message)
                }.collect { result ->
                    _tricountsList.value = result
                }
        }
    }

    fun onShowAddTricountDialogClick() {
        _showAddTricountDialog.value = true
    }

    fun onDismissAddTricountDialog() {
        _showAddTricountDialog.value = false
    }

    fun onTricountAdded(
        tricountModel: TricountUiModel,
        tricountParticipants: List<ParticipantUiModel>
    ) {
        _showAddTricountDialog.value = false

        viewModelScope.launch {
            getLoggedUserUseCase()?.let { loggerUser ->
                val newTricount = tricountModel.copy(
                    createdBy = loggerUser.id,
                    createdAt = Date.from(Instant.now())
                )

                addTricountUseCase(
                    tricountUiModel = newTricount
                )

                tricountParticipants
                    .filter { it.name.isNotEmpty() }
                    .forEach {
                        addParticipantUseCase(
                            it.copy(
                                tricountId = newTricount.id,
                                joinedAt = newTricount.createdAt
                            )
                        )
                    }
            }
        }
    }
}
