package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM ${TricountContract.TABLE_EXPENSE}")
    fun getAllFlow(): Flow<List<ExpenseEntity>>

    @Query(
        "SELECT * FROM ${TricountContract.TABLE_EXPENSE}" +
                " WHERE ${TricountContract.TABLE_EXPENSE_COLUMN_ID} = :expenseId"
    )
    fun getExpenseFlow(expenseId: ExpenseId): Flow<ExpenseEntity>

    @Query(
        "SELECT * FROM ${TricountContract.TABLE_EXPENSE}" +
                " WHERE ${TricountContract.TABLE_EXPENSE_COLUMN_TRICOUNT_ID} = :tricountId"
    )
    fun getExpensesFromTricountFlow(tricountId: TricountId): Flow<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: ExpenseEntity)

    @Update
    suspend fun update(user: ExpenseEntity): Int

    @Delete
    suspend fun delete(user: ExpenseEntity): Int
}
