package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.data.database.dao.ExpenseDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.model.ExpenseItem
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    fun getExpenses() = expenseDao
        .getAll()
        .map { expenseEntities ->
            expenseEntities.map { it.toDomain() }
        }

    suspend fun insertExpense(expense: ExpenseItem) {
        expenseDao.insert(expense.toDatabase())
    }

    suspend fun updateExpense(expense: ExpenseItem) {
        expenseDao.update(expense.toDatabase())
    }

    suspend fun deleteExpense(expense: ExpenseItem) {
        expenseDao.delete(expense.toDatabase())
    }
}
