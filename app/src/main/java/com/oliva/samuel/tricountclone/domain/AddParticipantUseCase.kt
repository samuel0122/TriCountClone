package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.ParticipantRepository
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import javax.inject.Inject

class AddParticipantUseCase @Inject constructor(
    private val participantRepository: ParticipantRepository
) {
    suspend operator fun invoke(participantModel: ParticipantModel) =
        participantRepository.insertParticipant(participantModel)
}
