package com.oliva.samuel.tricountclone.ui.screens.splashScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.domain.AddUserUseCase
import com.oliva.samuel.tricountclone.domain.GetLoggedUserUseCase
import com.oliva.samuel.tricountclone.domain.SetLoggedUserUseCase
import com.oliva.samuel.tricountclone.ui.model.LoggedUserUiModel
import com.oliva.samuel.tricountclone.ui.model.UserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val addUserUseCase: AddUserUseCase,
    private val setLoggedUserUseCase: SetLoggedUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    private val _effect = MutableSharedFlow<SplashEffect>()
    val effect: SharedFlow<SplashEffect> = _effect

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

    fun onEvent(event: SplashEvent) {
        when (event) {
            SplashEvent.OnAnimationFinished -> {
                viewModelScope.launch {
                    delay(500)
                    _effect.emit(SplashEffect.NavigateToTricounts)
                }
            }
        }
    }
}

sealed class SplashEvent {
    data object OnAnimationFinished : SplashEvent()
}

data class SplashState(
    val isLoading: Boolean = true
)

sealed class SplashEffect {
    data object NavigateToTricounts : SplashEffect()
}

sealed interface SplashUiState {
    data object Loading : SplashUiState
    data object NavigateToMain : SplashUiState
}
