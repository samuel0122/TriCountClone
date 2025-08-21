package com.oliva.samuel.tricountclone.domain.model

import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.ParticipantId

data class ExpenseShareModel(
    val expenseId: ExpenseId,
    val participantId: ParticipantId,
    val amountOwed: Double
) {
    companion object {
        fun default(
            expenseId: ExpenseId = ExpenseId.randomUUID(),
            participantId: ParticipantId = ParticipantId.randomUUID()
        ): ExpenseShareModel = ExpenseShareModel(
            expenseId = expenseId,
            participantId = participantId,
            amountOwed = 0.0
        )
    }
}
