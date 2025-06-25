package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.data.database.dao.ParticipantDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.model.ParticipantItem
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ParticipantRepository @Inject constructor(
    private val participantDao: ParticipantDao
) {
    fun getParticipants() = participantDao
        .getAll()
        .map { participantEntities ->
            participantEntities.map { it.toDomain() }
        }

    suspend fun insertParticipant(participant: ParticipantItem) {
        participantDao.insert(participant.toDatabase())
    }

    suspend fun updateParticipant(participant: ParticipantItem) {
        participantDao.update(participant.toDatabase())
    }

    suspend fun deleteParticipant(participant: ParticipantItem) {
        participantDao.delete(participant.toDatabase())
    }
}
