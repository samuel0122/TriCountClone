package com.oliva.samuel.tricountclone.domain.model

import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.core.UserId
import java.time.Instant
import java.util.Date

data class ParticipantModel(
    val id: ParticipantId,
    val name: String,
    val joinedAt: Date,
    val userId: UserId?,
    val tricountId: TricountId
) {
    companion object {
        fun default(
            tricountId: TricountId = TricountId.randomUUID()
        ): ParticipantModel = ParticipantModel(
            id = ParticipantId.randomUUID(),
            name = "",
            joinedAt = Date.from(Instant.now()),
            userId = null,
            tricountId = tricountId
        )
    }
}
