package com.oliva.samuel.tricountclone.domain.model

import java.util.Date
import java.util.UUID

data class ParticipantModel(
    val id: UUID,
    val name: String,
    val joinedAt: Date,
    val userId: UUID?,
    val tricountId: UUID
)
