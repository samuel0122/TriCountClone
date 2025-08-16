package com.oliva.samuel.tricountclone.ui.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.domain.AddUserUseCase
import com.oliva.samuel.tricountclone.domain.GetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.SetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.model.LoggedUserModel
import com.oliva.samuel.tricountclone.domain.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
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
                val addedUser = UserModel(
                    id = UUID.randomUUID(),
                    name = "Samuel"
                )
                addUserUseCase(addedUser)
                setLoggedUserUseCase(LoggedUserModel(addedUser.id))
            }
        }
    }
}
