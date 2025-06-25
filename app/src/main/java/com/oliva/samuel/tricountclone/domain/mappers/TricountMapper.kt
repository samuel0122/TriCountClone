package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.domain.model.TricountItem

fun TricountEntity.toDomain() = TricountItem(
    id = id,
    title = title,
    icon = icon,
    currency = currency,
    createdBy = createdBy,
    createdAt = createdAt
)

fun TricountItem.toDatabase() = TricountEntity(
    id = id,
    title = title,
    icon = icon,
    currency = currency,
    createdBy = createdBy,
    createdAt = createdAt
)
