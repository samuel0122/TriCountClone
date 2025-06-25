package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.ParticipantEntity
import com.oliva.samuel.tricountclone.domain.model.ParticipantItem

fun ParticipantEntity.toDomain() = ParticipantItem(
    id = id,
    name = name,
    joinedAt = joinedAt,
    userId = userId,
    tricountId = tricountId
)

fun ParticipantItem.toDatabase() = ParticipantEntity(
    id = id,
    name = name,
    joinedAt = joinedAt,
    userId = userId,
    tricountId = tricountId
)
