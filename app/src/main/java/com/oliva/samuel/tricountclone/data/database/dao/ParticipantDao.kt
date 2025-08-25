package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.ParticipantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipantDao {
    @Query(
        "SELECT * FROM ${TricountContract.TABLE_PARTICIPANT}" +
                " WHERE ${TricountContract.TABLE_PARTICIPANT_COLUMN_ID} = :participantId"
    )
    fun getParticipantFlow(participantId: ParticipantId): Flow<ParticipantEntity>

    @Query(
        "SELECT * FROM ${TricountContract.TABLE_PARTICIPANT}" +
                " WHERE ${TricountContract.TABLE_PARTICIPANT_COLUMN_ID} IN (:ids)"
    )
    fun getParticipantsFlow(ids: List<ParticipantId>): Flow<List<ParticipantEntity>>

    @Query(
        "SELECT * FROM ${TricountContract.TABLE_PARTICIPANT}" +
                " WHERE ${TricountContract.TABLE_PARTICIPANT_COLUMN_TRICOUNT_ID} = :tricountId"
    )
    fun getParticipantsFromTricountFlow(tricountId: TricountId): Flow<List<ParticipantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: ParticipantEntity)

    @Update
    suspend fun update(user: ParticipantEntity): Int

    @Delete
    suspend fun delete(user: ParticipantEntity): Int
}
