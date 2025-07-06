package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.relations.TricountWithParticipantsAndExpensesRelation
import com.oliva.samuel.tricountclone.domain.model.TricountWithParticipantsAndExpensesModel

fun TricountWithParticipantsAndExpensesRelation.toDomain() =
    TricountWithParticipantsAndExpensesModel(
        tricount = tricount.toDomain(),
        participants = participants.map { it.toDomain() },
        expenses = expenses.map { it.toDomain() }
    )

fun TricountWithParticipantsAndExpensesModel.toDatabase() =
    TricountWithParticipantsAndExpensesRelation(
        tricount = tricount.toDatabase(),
        participants = participants.map { it.toDatabase() },
        expenses = expenses.map { it.toDatabase() }
    )
