package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.UserRepository
import com.oliva.samuel.tricountclone.domain.mappers.toUiModel
import com.oliva.samuel.tricountclone.ui.model.UserUiModel
import javax.inject.Inject

class GetLoggedUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): UserUiModel? = userRepository
        .getLoggedUser()?.toUiModel()
}
