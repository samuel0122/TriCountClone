package com.oliva.samuel.tricountclone.ui.model

import com.oliva.samuel.tricountclone.core.ExpenseId
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.core.TricountId
import java.util.Date

data class ExpenseUiModel(
    val id: ExpenseId,
    val title: String,
    val amount: Double,
    val paidBy: ParticipantId,
    val createdAt: Date,
    val tricountId: TricountId,
    val note: String?
)
