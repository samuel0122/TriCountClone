package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.ExpenseShareEntity
import com.oliva.samuel.tricountclone.domain.model.ExpenseShareModel
import com.oliva.samuel.tricountclone.ui.model.ExpenseShareUiModel

fun ExpenseShareEntity.toDomain() = ExpenseShareModel(
    expenseId = expenseId,
    participantId = participantId,
    amountOwed = amountOwed
)

fun ExpenseShareModel.toDatabase() = ExpenseShareEntity(
    expenseId = expenseId,
    participantId = participantId,
    amountOwed = amountOwed
)

fun ExpenseShareModel.toUiModel() = ExpenseShareUiModel(
    expenseId = expenseId,
    participantId = participantId,
    amountOwed = amountOwed
)


fun ExpenseShareUiModel.toDomain() = ExpenseShareModel(
    expenseId = expenseId,
    participantId = participantId,
    amountOwed = amountOwed
)
