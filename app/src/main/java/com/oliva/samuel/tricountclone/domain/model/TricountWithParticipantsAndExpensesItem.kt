package com.oliva.samuel.tricountclone.domain.model

data class TricountWithParticipantsAndExpensesItem(
    val tricountItem: TricountItem,
    val participants: List<ParticipantItem>,
    val expenses: List<ExpenseItem>
)
