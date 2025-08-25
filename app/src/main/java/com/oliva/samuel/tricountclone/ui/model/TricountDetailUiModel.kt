package com.oliva.samuel.tricountclone.ui.model

import com.oliva.samuel.tricountclone.core.ParticipantId

data class TricountDetailUiModel(
    val tricount: TricountUiModel,
    val createdBy: UserUiModel,
    val participants: Map<ParticipantId, ParticipantUiModel>,
    val expenses: List<ExpenseUiModel>
)
