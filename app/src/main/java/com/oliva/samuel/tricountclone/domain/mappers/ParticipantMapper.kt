package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.ParticipantEntity
import com.oliva.samuel.tricountclone.domain.model.ParticipantModel

fun ParticipantEntity.toDomain() = ParticipantModel(
    id = id,
    name = name,
    joinedAt = joinedAt,
    userId = userId,
    tricountId = tricountId
)

fun ParticipantModel.toDatabase() = ParticipantEntity(
    id = id,
    name = name,
    joinedAt = joinedAt,
    userId = userId,
    tricountId = tricountId
)
