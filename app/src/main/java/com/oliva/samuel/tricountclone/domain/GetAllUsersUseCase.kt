package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() =
        userRepository.getUsers()
}
