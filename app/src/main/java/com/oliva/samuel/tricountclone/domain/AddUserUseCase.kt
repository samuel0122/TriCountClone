package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.UserRepository
import com.oliva.samuel.tricountclone.domain.model.UserModel
import javax.inject.Inject

class AddUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userModel: UserModel) =
        userRepository.insertUser(userModel)
}
