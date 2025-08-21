package com.oliva.samuel.tricountclone.ui.model

import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.ParticipantId

data class ExpenseShareUiModel(
    val expenseId: ExpenseId,
    val participantId: ParticipantId,
    val amountOwed: Double
)
