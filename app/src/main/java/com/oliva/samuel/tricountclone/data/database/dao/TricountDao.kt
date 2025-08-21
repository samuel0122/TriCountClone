package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.data.database.entities.relations.TricountWithParticipantsAndExpensesRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface TricountDao {
    @Query("SELECT * FROM ${TricountContract.TABLE_TRICOUNT}")
    fun getAllFlow(): Flow<List<TricountEntity>>

    @Query(
        "SELECT * FROM ${TricountContract.TABLE_TRICOUNT} " +
                "WHERE ${TricountContract.TABLE_TRICOUNT_COLUMN_ID} = :tricountId"
    )
    fun getTricountFlow(tricountId: TricountId): Flow<TricountEntity>

    @Query(
        "SELECT * FROM ${TricountContract.TABLE_TRICOUNT} " +
                "WHERE ${TricountContract.TABLE_TRICOUNT_COLUMN_CREATED_BY} = :userId"
    )
    fun getTricountsFromUserFlow(userId: UserId): Flow<List<TricountEntity>>

    @Transaction
    @Query(
        "SELECT * FROM ${TricountContract.TABLE_TRICOUNT} " +
                "WHERE ${TricountContract.TABLE_TRICOUNT_COLUMN_ID} = :tricountId"
    )
    fun getTricountWithParticipantsAndExpensesFlow(tricountId: TricountId): Flow<TricountWithParticipantsAndExpensesRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: TricountEntity)

    @Update
    suspend fun update(user: TricountEntity): Int

    @Delete
    suspend fun delete(user: TricountEntity): Int
}
