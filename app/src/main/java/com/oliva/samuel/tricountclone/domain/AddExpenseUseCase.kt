package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.ExpenseRepository
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expense: ExpenseModel) =
        expenseRepository.insertExpense(expense)
}
