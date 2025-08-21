package com.oliva.samuel.tricountclone.domain.model

import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.core.UserId
import java.time.Instant
import java.util.Date

data class TricountModel(
    val id: TricountId,
    var title: String,
    var icon: String,
    var currency: Currency,
    val createdBy: UserId,
    val createdAt: Date
) {
    companion object {
        fun default(
            createdBy: UserId = UserId.randomUUID()
        ): TricountModel = TricountModel(
            id = TricountId.randomUUID(),
            title = "",
            icon = "🏖️",
            currency = Currency.Euro,
            createdBy = createdBy,
            createdAt = Date.from(Instant.now())
        )
    }
}
