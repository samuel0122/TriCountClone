package com.oliva.samuel.tricountclone.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import java.util.Date
import java.util.UUID

@Entity(
    tableName = TricountContract.TABLE_EXPENSE,
    foreignKeys = [
        ForeignKey(
            entity = ParticipantEntity::class,
            parentColumns = [TricountContract.TABLE_PARTICIPANT_COLUMN_ID],
            childColumns = [TricountContract.TABLE_EXPENSE_COLUMN_PAID_BY],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TricountEntity::class,
            parentColumns = [TricountContract.TABLE_TRICOUNT_COLUMN_ID],
            childColumns = [TricountContract.TABLE_EXPENSE_COLUMN_TRICOUNT_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(TricountContract.TABLE_EXPENSE_COLUMN_PAID_BY),
        Index(TricountContract.TABLE_EXPENSE_COLUMN_TRICOUNT_ID)
    ]
)
data class ExpenseEntity(
    @PrimaryKey
    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_COLUMN_ID)
    val id: UUID = UUID.randomUUID(),

    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_COLUMN_AMOUNT)
    val amount: Double,

    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_COLUMN_PAID_BY)
    val paidBy: UUID,

    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_COLUMN_CREATED_AT)
    val createdAt: Date,

    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_COLUMN_TRICOUNT_ID)
    val tricountId: UUID,

    @ColumnInfo(name = TricountContract.TABLE_EXPENSE_COLUMN_NOTE)
    val note: String?
)
