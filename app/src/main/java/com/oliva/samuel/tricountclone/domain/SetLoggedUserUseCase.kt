package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.UserRepository
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.ui.model.LoggedUserUiModel
import javax.inject.Inject

class SetLoggedUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(loggedUserUiModel: LoggedUserUiModel) = userRepository
        .setLoggedUser(loggedUserUiModel.toDomain())
}
