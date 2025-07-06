package com.oliva.samuel.tricountclone.domain.model

data class TricountWithParticipantsAndExpensesModel(
    val tricount: TricountModel,
    val participants: List<ParticipantModel>,
    val expenses: List<ExpenseModel>
)
