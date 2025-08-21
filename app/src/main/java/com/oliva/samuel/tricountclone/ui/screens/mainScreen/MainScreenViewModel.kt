package com.oliva.samuel.tricountclone.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.domain.AddUserUseCase
import com.oliva.samuel.tricountclone.domain.GetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.SetLoggedUserUseCase
import com.oliva.samuel.tricountclone.ui.model.LoggedUserUiModel
import com.oliva.samuel.tricountclone.ui.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    getLoggedUserUseCase: GetLoggedUserUseCase,
    addUserUseCase: AddUserUseCase,
    setLoggedUserUseCase: SetLoggedUserUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {
            if (getLoggedUserUseCase() == null) {
                val addedUser = UserUiModel(
                    id = UserId.randomUUID(),
                    name = "Samuel"
                )
                addUserUseCase(addedUser)
                setLoggedUserUseCase(LoggedUserUiModel(addedUser))
            }
        }
    }
}
