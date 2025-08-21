package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.ParticipantRepository
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.ui.model.ParticipantUiModel
import javax.inject.Inject

class AddParticipantUseCase @Inject constructor(
    private val participantRepository: ParticipantRepository
) {
    suspend operator fun invoke(participantUiModel: ParticipantUiModel) =
        participantRepository.insertParticipant(participantUiModel.toDomain())
}
