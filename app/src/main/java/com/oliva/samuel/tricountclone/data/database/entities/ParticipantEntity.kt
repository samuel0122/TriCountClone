package com.oliva.samuel.tricountclone.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oliva.samuel.tricountclone.core.ParticipantId
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import java.util.Date

@Entity(
    tableName = TricountContract.TABLE_PARTICIPANT,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [TricountContract.TABLE_USERS_COLUMN_ID],
            childColumns = [TricountContract.TABLE_PARTICIPANT_COLUMN_USER_ID],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = TricountEntity::class,
            parentColumns = [TricountContract.TABLE_TRICOUNT_COLUMN_ID],
            childColumns = [TricountContract.TABLE_PARTICIPANT_COLUMN_TRICOUNT_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(TricountContract.TABLE_PARTICIPANT_COLUMN_USER_ID),
        Index(TricountContract.TABLE_PARTICIPANT_COLUMN_TRICOUNT_ID)
    ]
)
data class ParticipantEntity(
    @PrimaryKey
    @ColumnInfo(name = TricountContract.TABLE_PARTICIPANT_COLUMN_ID)
    val id: ParticipantId = ParticipantId.randomUUID(),

    @ColumnInfo(name = TricountContract.TABLE_PARTICIPANT_COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = TricountContract.TABLE_PARTICIPANT_COLUMN_JOINED_AT)
    val joinedAt: Date,

    @ColumnInfo(name = TricountContract.TABLE_PARTICIPANT_COLUMN_USER_ID)
    val userId: UserId?,

    @ColumnInfo(name = TricountContract.TABLE_PARTICIPANT_COLUMN_TRICOUNT_ID)
    val tricountId: TricountId
)
