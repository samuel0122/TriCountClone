package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM ${TricountContract.TABLE_EXPENSE}")
    fun getAll(): Flow<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: ExpenseEntity)

    @Update
    suspend fun update(user: ExpenseEntity): Int

    @Delete
    suspend fun delete(user: ExpenseEntity): Int
}
