package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.UserEntity
import com.oliva.samuel.tricountclone.domain.model.UserModel

fun UserEntity.toDomain() = UserModel(
    id = id,
    name = name
)

fun UserModel.toDatabase() = UserEntity(
    id = id,
    name = name
)
