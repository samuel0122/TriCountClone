package com.oliva.samuel.tricountclone.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.ExpenseEntity
import com.oliva.samuel.tricountclone.data.database.entities.ParticipantEntity
import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity

data class TricountWithParticipantsAndExpensesRelation(
    @Embedded val tricount: TricountEntity,
    @Relation(
        entity = ParticipantEntity::class,
        parentColumn = TricountContract.TABLE_TRICOUNT_COLUMN_ID,
        entityColumn = TricountContract.TABLE_PARTICIPANT_COLUMN_TRICOUNT_ID
    )
    val participants: List<ParticipantEntity>,
    @Relation(
        entity = ExpenseEntity::class,
        parentColumn = TricountContract.TABLE_TRICOUNT_COLUMN_ID,
        entityColumn = TricountContract.TABLE_EXPENSE_COLUMN_TRICOUNT_ID
    )
    val expenses: List<ExpenseEntity>
)
