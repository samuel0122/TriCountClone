package com.oliva.samuel.tricountclone.domain.model

import java.time.Instant
import java.util.Date
import java.util.UUID

data class ExpenseModel(
    val id: UUID,
    val title: String,
    val amount: Double,
    val paidBy: UUID,
    val createdAt: Date,
    val tricountId: UUID,
    val note: String?
) {
    companion object {
        fun default(): ExpenseModel = ExpenseModel(
            id = UUID.randomUUID(),
            title = "Default Expense",
            amount = 2.21,
            paidBy = UUID.randomUUID(),
            createdAt = Date.from(Instant.now()),
            tricountId = UUID.randomUUID(),
            note = null
        )
    }
}
