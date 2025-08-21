package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.ExpenseRepository
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    suspend operator fun invoke(expenseUiModel: ExpenseUiModel) =
        expenseRepository.insertExpense(expenseUiModel.toDomain())
}
