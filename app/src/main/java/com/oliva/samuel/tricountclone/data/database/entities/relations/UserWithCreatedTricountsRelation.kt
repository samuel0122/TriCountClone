package com.oliva.samuel.tricountclone.data.database.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.data.database.entities.UserEntity

data class UserWithCreatedTricountsRelation (
    @Embedded val user: UserEntity,
    @Relation(
        entity = TricountEntity::class,
        parentColumn = TricountContract.TABLE_USERS_COLUMN_ID,
        entityColumn = TricountContract.TABLE_TRICOUNT_COLUMN_CREATED_BY
    )
    val tricounts : List<TricountEntity>
)
