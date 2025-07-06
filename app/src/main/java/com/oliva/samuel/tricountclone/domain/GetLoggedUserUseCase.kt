package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.UserRepository
import javax.inject.Inject

class GetLoggedUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke() = userRepository.getLoggedUser()
}
