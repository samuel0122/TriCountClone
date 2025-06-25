package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.ExpenseShareEntity
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareItem
import java.util.UUID

fun ExpenseShareEntity.toDomain() = ExpenseShareItem(
    amountOwed = amountOwed
)

fun ExpenseShareItem.toDatabase(
    expenseId: UUID,
    participantId: UUID
) = ExpenseShareEntity(
    expenseId = expenseId,
    participantId = participantId,
    amountOwed = amountOwed
)
