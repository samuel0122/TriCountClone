package com.oliva.samuel.tricountclone.domain.model

import java.time.Instant
import java.util.Date
import java.util.UUID

data class ParticipantModel(
    val id: UUID,
    val name: String,
    val joinedAt: Date,
    val userId: UUID?,
    val tricountId: UUID
) {
    companion object {
        fun default(
            tricountId: UUID = UUID.randomUUID()
        ): ParticipantModel = ParticipantModel(
            id = UUID.randomUUID(),
            name = "",
            joinedAt = Date.from(Instant.now()),
            userId = null,
            tricountId = tricountId
        )
    }
}
