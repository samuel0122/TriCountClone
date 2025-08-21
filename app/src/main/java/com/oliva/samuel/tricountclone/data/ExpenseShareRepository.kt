package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.data.database.dao.ExpenseShareDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExpenseShareRepository @Inject constructor(
    private val expenseShareDao: ExpenseShareDao
) {
    fun getExpensesShares(): Flow<List<ExpenseShareModel>> = expenseShareDao
        .getAllFlow()
        .map { expenseEntities ->
            expenseEntities.map { it.toDomain() }
        }

    fun getExpenseShares(expenseId: ExpenseId): Flow<List<ExpenseShareModel>> = expenseShareDao
        .getSharesFromExpenseFlow(expenseId)
        .map { tricountEntity ->
            tricountEntity.map { it.toDomain() }
        }

    suspend fun insertExpense(expenseShare: ExpenseShareModel) {
        expenseShareDao.insert(expenseShare.toDatabase())
    }

    suspend fun updateExpense(expenseShare: ExpenseShareModel) {
        expenseShareDao.update(expenseShare.toDatabase())
    }

    suspend fun deleteExpense(expenseShare: ExpenseShareModel) {
        expenseShareDao.delete(expenseShare.toDatabase())
    }
}
