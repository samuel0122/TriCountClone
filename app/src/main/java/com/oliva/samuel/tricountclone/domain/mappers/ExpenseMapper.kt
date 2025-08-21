package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.ExpenseEntity
import com.oliva.samuel.tricountclone.domain.model.ExpenseModel
import com.oliva.samuel.tricountclone.ui.model.ExpenseUiModel

fun ExpenseEntity.toDomain() = ExpenseModel(
    id = id,
    title = title,
    amount = amount,
    paidBy = paidBy,
    createdAt = createdAt,
    tricountId = tricountId,
    note = note
)

fun ExpenseModel.toDatabase() = ExpenseEntity(
    id = id,
    title = title,
    amount = amount,
    paidBy = paidBy,
    createdAt = createdAt,
    tricountId = tricountId,
    note = note
)

fun ExpenseModel.toUiModel() = ExpenseUiModel(
    id = id,
    title = title,
    amount = amount,
    paidBy = paidBy,
    createdAt = createdAt,
    tricountId = tricountId,
    note = note
)

fun ExpenseUiModel.toDomain() = ExpenseModel(
    id = id,
    title = title,
    amount = amount,
    paidBy = paidBy,
    createdAt = createdAt,
    tricountId = tricountId,
    note = note
)
