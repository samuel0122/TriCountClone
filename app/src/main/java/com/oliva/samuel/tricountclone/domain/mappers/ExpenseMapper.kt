package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.ExpenseEntity
import com.oliva.samuel.tricountclone.domain.model.ExpenseItem

fun ExpenseEntity.toDomain() = ExpenseItem(
    id = id,
    title = title,
    amount = amount,
    paidBy = paidBy,
    createdAt = createdAt,
    tricountId = tricountId,
    note = note
)

fun ExpenseItem.toDatabase() = ExpenseEntity(
    id = id,
    title = title,
    amount = amount,
    paidBy = paidBy,
    createdAt = createdAt,
    tricountId = tricountId,
    note = note
)
