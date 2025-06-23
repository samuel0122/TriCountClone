package com.oliva.samuel.tricountclone.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import java.util.UUID

@Entity(
    tableName = TricountContract.TABLE_EXPENSE_SHARE,
    primaryKeys = [
        TricountContract.TABLE_EXPENSE_SHARE_COLUMN_ID,
        TricountContract.TABLE_EXPENSE_SHARE_COLUMN_PARTICIPANTS_ID
    ],
    foreignKeys = [
        ForeignKey(
            entity = ExpenseEntity::class,
            parentColumns = [TricountContract.TABLE_EXPENSE_COLUMN_ID],
            childColumns = [TricountContract.TABLE_EXPENSE_SHARE_COLUMN_ID],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ParticipantEntity::class,
            parentColumns = [TricountContract.TABLE_PARTICIPANT_COLUMN_ID],
            childColumns = [TricountContract.TABLE_EXPENSE_SHARE_COLUMN_PARTICIPANTS_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(TricountContract.TABLE_EXPENSE_SHARE_COLUMN_ID),
        Index(TricountContract.TABLE_EXPENSE_SHARE_COLUMN_PARTICIPANTS_ID)
    ]
)
data class ExpenseShareEntity(
    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_SHARE_COLUMN_ID)
    val expenseId: UUID,

    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_SHARE_COLUMN_PARTICIPANTS_ID)
    val participantId: UUID,

    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_SHARE_COLUMN_AMOUNT_OWNED)
    val amountOwed: Double
)
