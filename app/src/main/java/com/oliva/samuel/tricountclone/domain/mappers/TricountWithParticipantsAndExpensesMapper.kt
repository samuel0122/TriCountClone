package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.relations.TricountWithParticipantsAndExpensesRelation
import com.oliva.samuel.tricountclone.domain.model.TricountWithParticipantsAndExpensesItem

fun TricountWithParticipantsAndExpensesRelation.toDomain() =
    TricountWithParticipantsAndExpensesItem(
        tricountItem = tricount.toDomain(),
        participants = participants.map { it.toDomain() },
        expenses = expenses.map { it.toDomain() }
    )

fun TricountWithParticipantsAndExpensesItem.toDatabase() =
    TricountWithParticipantsAndExpensesRelation(
        tricount = tricountItem.toDatabase(),
        participants = participants.map { it.toDatabase() },
        expenses = expenses.map { it.toDatabase() }
    )
