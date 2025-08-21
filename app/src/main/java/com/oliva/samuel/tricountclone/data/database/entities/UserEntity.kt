package com.oliva.samuel.tricountclone.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract

@Entity(
    tableName = TricountContract.TABLE_USERS
)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = TricountContract.TABLE_USERS_COLUMN_ID)
    val id: UserId = UserId.randomUUID(),

    @ColumnInfo(name = TricountContract.TABLE_USERS_COLUMN_NAME)
    val name: String
)
