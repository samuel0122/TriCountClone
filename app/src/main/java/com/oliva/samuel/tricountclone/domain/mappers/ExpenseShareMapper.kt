package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.ExpenseShareEntity
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import java.util.UUID

fun ExpenseShareEntity.toDomain() = ExpenseShareModel(
    amountOwed = amountOwed
)

fun ExpenseShareModel.toDatabase(
    expenseId: UUID,
    participantId: UUID
) = ExpenseShareEntity(
    expenseId = expenseId,
    participantId = participantId,
    amountOwed = amountOwed
)
