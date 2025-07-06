package com.oliva.samuel.tricountclone.ui.screens.tricountsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.domain.AddTricountUseCase
import com.oliva.samuel.tricountclone.domain.AddUserUseCase
import com.oliva.samuel.tricountclone.domain.GetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.GetTricountUseCase
import com.oliva.samuel.tricountclone.domain.SetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.model.LoggedUserModel
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.domain.model.UserModel
import com.oliva.samuel.tricountclone.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class TricountsScreenViewModel @Inject constructor(
    getTricountUseCase: GetTricountUseCase,
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val addTricountUseCase: AddTricountUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val setLoggedUserUseCase: SetLoggedUserUseCase
) : ViewModel() {
    val tricountsList: StateFlow<Resource<List<TricountModel>>> = getTricountUseCase().map {
        Resource.Success(it)
    }.catch { error ->
        Resource.Error<List<TricountModel>>(error, error.message)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000), // Dar 5 segundos de timeout antes de detener el flow
        Resource.Loading
    )

    private val _showAddTricountDialog = MutableLiveData<Boolean>()
    val showAddTricountDialog: LiveData<Boolean> = _showAddTricountDialog

    fun onShowAddTricountDialogClick() {
        _showAddTricountDialog.value = true
    }

    fun onDismissAddTricountDialog() {
        _showAddTricountDialog.value = false
    }

    fun onTricountAdded(tricountModel: TricountModel) {
        _showAddTricountDialog.value = false

        viewModelScope.launch {
            if (getLoggedUserUseCase() == null) {
                val addedUser = UserModel(
                    id = UUID.randomUUID(),
                    name = "Samuel"
                )
                addUserUseCase(addedUser)
                setLoggedUserUseCase(LoggedUserModel(addedUser.id))
            }
            getLoggedUserUseCase()?.let { loggerUser ->
                addTricountUseCase(
                    tricountModel = tricountModel.copy(
                        id = UUID.randomUUID(),
                        createdBy = loggerUser.id,
                        createdAt = Date.from(Instant.now())
                    )
                )
            }
        }
    }
}
