package com.oliva.samuel.tricountclone.ui.model

data class TricountDetailUiModel(
    val tricount: TricountUiModel,
    val createdBy: UserUiModel,
    val participants: List<ParticipantUiModel>,
    val expenses: List<ExpenseUiModel>
)
