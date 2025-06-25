package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.ExpenseShareEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseShareDao {
    @Query("SELECT * FROM ${TricountContract.TABLE_EXPENSE_SHARE}")
    fun getAll(): Flow<List<ExpenseShareEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: ExpenseShareEntity)

    @Update
    suspend fun update(user: ExpenseShareEntity): Int

    @Delete
    suspend fun delete(user: ExpenseShareEntity): Int
}
