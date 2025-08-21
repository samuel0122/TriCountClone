package com.oliva.samuel.tricountclone.ui.model

import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.core.UserId
import java.util.Date

data class ParticipantUiModel(
    val id: ParticipantId,
    val name: String,
    val joinedAt: Date,
    val userId: UserId?,
    val tricountId: TricountId
)
