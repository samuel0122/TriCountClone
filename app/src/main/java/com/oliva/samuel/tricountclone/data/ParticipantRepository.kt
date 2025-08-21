package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.data.database.dao.ParticipantDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ParticipantRepository @Inject constructor(
    private val participantDao: ParticipantDao
) {
    fun getParticipants(): Flow<List<ParticipantModel>> = participantDao
        .getAllFlow()
        .map { participantEntities ->
            participantEntities.map { it.toDomain() }
        }

    fun getParticipant(participantId: ParticipantId): Flow<ParticipantModel> = participantDao
        .getParticipantFlow(participantId)
        .map { participantEntity ->
            participantEntity.toDomain()
        }

    fun getParticipantsFromTricount(tricountId: TricountId): Flow<List<ParticipantModel>> =
        participantDao
            .getParticipantsFromTricountFlow(tricountId)
            .map { participantEntity ->
                participantEntity.map { it.toDomain() }
            }

    suspend fun insertParticipant(participant: ParticipantModel) {
        participantDao.insert(participant.toDatabase())
    }

    suspend fun updateParticipant(participant: ParticipantModel) {
        participantDao.update(participant.toDatabase())
    }

    suspend fun deleteParticipant(participant: ParticipantModel) {
        participantDao.delete(participant.toDatabase())
    }
}
