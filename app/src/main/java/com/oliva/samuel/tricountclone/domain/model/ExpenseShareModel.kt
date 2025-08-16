package com.oliva.samuel.tricountclone.domain.model

import java.util.UUID

data class ExpenseShareModel(
    val expenseId: UUID,
    val participantId: UUID,
    val amountOwed: Double
) {
    companion object {
        fun default(): ExpenseShareModel = ExpenseShareModel(
            expenseId = UUID.randomUUID(),
            participantId = UUID.randomUUID(),
            amountOwed = 0.0
        )
    }
}
