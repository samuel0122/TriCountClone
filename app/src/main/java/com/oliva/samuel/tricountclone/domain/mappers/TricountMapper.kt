package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.domain.model.TricountModel

fun TricountEntity.toDomain() = TricountModel(
    id = id,
    title = title,
    icon = icon,
    currency = currency,
    createdBy = createdBy,
    createdAt = createdAt
)

fun TricountModel.toDatabase() = TricountEntity(
    id = id,
    title = title,
    icon = icon,
    currency = currency,
    createdBy = createdBy,
    createdAt = createdAt
)
