package com.oliva.samuel.tricountclone.ui.model

import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.domain.model.Currency
import java.util.Date

data class TricountUiModel(
    val id: TricountId,
    var title: String,
    var icon: String,
    var currency: Currency,
    val createdBy: UserId,
    val createdAt: Date
)
