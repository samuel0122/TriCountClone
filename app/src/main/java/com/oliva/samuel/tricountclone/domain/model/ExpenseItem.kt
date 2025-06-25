package com.oliva.samuel.tricountclone.domain.model

import java.util.Date
import java.util.UUID

data class ExpenseItem(
    val id: UUID,
    val title: String,
    val amount: Double,
    val paidBy: UUID,
    val createdAt: Date,
    val tricountId: UUID,
    val note: String?
)
