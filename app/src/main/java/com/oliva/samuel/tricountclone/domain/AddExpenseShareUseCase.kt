package com.oliva.samuel.tricountclone.domain

import com.oliva.samuel.tricountclone.data.ExpenseShareRepository
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.ui.model.ExpenseShareUiModel
import javax.inject.Inject

class AddExpenseShareUseCase @Inject constructor(
    private val expenseShareRepository: ExpenseShareRepository
) {
    suspend operator fun invoke(expenseShareUiModel: ExpenseShareUiModel) =
        expenseShareRepository.insertExpense(expenseShareUiModel.toDomain())
}
