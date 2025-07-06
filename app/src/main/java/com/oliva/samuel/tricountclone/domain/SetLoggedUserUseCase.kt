package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.UserRepository
import com.oliva.samuel.tricountclone.domain.model.LoggedUserModel
import javax.inject.Inject

class SetLoggedUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(loggedUserModel: LoggedUserModel) =
        userRepository.setLoggedUser(loggedUserModel)
}
