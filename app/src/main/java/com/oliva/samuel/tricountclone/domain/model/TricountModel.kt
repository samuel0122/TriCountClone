package com.oliva.samuel.tricountclone.domain.model

import java.time.Instant
import java.util.Date
import java.util.UUID

data class TricountModel(
    val id: UUID,
    var title: String,
    var icon: String,
    var currency: String,
    val createdBy: UUID,
    val createdAt: Date
) {
    companion object {
        fun default(): TricountModel = TricountModel(
            id = UUID.randomUUID(),
            title = "",
            icon = "",
            currency = "",
            createdBy = UUID.randomUUID(),
            createdAt = Date.from(Instant.now())
        )
    }
}
