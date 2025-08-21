package com.oliva.samuel.tricountclone.domain.model

import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.core.TricountId
import java.time.Instant
import java.util.Date

data class ExpenseModel(
    val id: ExpenseId,
    val title: String,
    val amount: Double,
    val paidBy: ParticipantId,
    val createdAt: Date,
    val tricountId: TricountId,
    val note: String?
) {
    companion object {
        fun default(
            paidBy: ParticipantId = ParticipantId.randomUUID(),
            tricountId: TricountId = TricountId.randomUUID()
        ): ExpenseModel = ExpenseModel(
            id = ExpenseId.randomUUID(),
            title = "Default Expense",
            amount = 2.21,
            paidBy = paidBy,
            createdAt = Date.from(Instant.now()),
            tricountId = tricountId,
            note = null
        )
    }
}
