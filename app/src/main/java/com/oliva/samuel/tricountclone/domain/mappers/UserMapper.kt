package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.UserEntity
import com.oliva.samuel.tricountclone.domain.model.UserItem

fun UserEntity.toDomain() = UserItem(
    id = id,
    name = name
)

fun UserItem.toDatabase() = UserEntity(
    id = id,
    name = name
)
