package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.data.database.entities.relations.TricountWithParticipantsAndExpenses
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TricountDao {
    @Query("SELECT * FROM ${TricountContract.TABLE_TRICOUNT}")
    fun getAll(): Flow<List<TricountEntity>>

    @Transaction
    @Query(
        "SELECT * FROM ${TricountContract.TABLE_TRICOUNT} " +
                "WHERE ${TricountContract.TABLE_TRICOUNT_COLUMN_ID} = :tricountId"
    )
    fun getTricountWithParticipantsAndExpenses(tricountId: UUID): Flow<TricountWithParticipantsAndExpenses>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: TricountEntity)

    @Update
    suspend fun update(user: TricountEntity): Int

    @Delete
    suspend fun delete(user: TricountEntity): Int
}
