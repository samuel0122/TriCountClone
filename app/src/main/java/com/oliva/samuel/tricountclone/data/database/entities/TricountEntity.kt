package com.oliva.samuel.tricountclone.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.oliva.samuel.tricountclone.core.TricountId
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import java.util.Date

@Entity(
    tableName = TricountContract.TABLE_TRICOUNT,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [TricountContract.TABLE_USERS_COLUMN_ID],
            childColumns = [TricountContract.TABLE_TRICOUNT_COLUMN_CREATED_BY],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(TricountContract.TABLE_TRICOUNT_COLUMN_CREATED_BY)]
)
data class TricountEntity(
    @PrimaryKey
    @ColumnInfo(name = TricountContract.TABLE_TRICOUNT_COLUMN_ID)
    val id: TricountId = TricountId.randomUUID(),

    @ColumnInfo(name = TricountContract.TABLE_TRICOUNT_COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = TricountContract.TABLE_TRICOUNT_COLUMN_ICON)
    val icon: String,

    @ColumnInfo(name = TricountContract.TABLE_TRICOUNT_COLUMN_CURRENCY)
    val currency: String,

    @ColumnInfo(name = TricountContract.TABLE_TRICOUNT_COLUMN_CREATED_BY)
    val createdBy: UserId,

    @ColumnInfo(name = TricountContract.TABLE_TRICOUNT_COLUMN_CREATED_AT)
    val createdAt: Date
)
