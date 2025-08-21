package com.oliva.samuel.tricountclone.ui.model

data class ExpenseDetailUiModel(
    val expenseUiModel: ExpenseUiModel,
    val paidBy: ParticipantUiModel,
    val expenseShares: List<ExpenseShareUiModel>
)
