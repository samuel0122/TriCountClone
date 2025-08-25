package com.oliva.samuel.tricountclone.ui.model

import com.oliva.samuel.tricountclone.core.ParticipantId

data class ExpenseDetailUiModel(
    val expenseUiModel: ExpenseUiModel,
    val paidBy: ParticipantUiModel,
    val expenseShares: List<ExpenseShareUiModel>,
    val expenseSharesParticipants: Map<ParticipantId,ParticipantUiModel>
)
