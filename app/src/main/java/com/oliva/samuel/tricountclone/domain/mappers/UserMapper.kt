package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.UserEntity
import com.oliva.samuel.tricountclone.domain.model.UserModel
import com.oliva.samuel.tricountclone.ui.model.UserUiModel

fun UserEntity.toDomain() = UserModel(
    id = id,
    name = name
)

fun UserModel.toDatabase() = UserEntity(
    id = id,
    name = name
)

fun UserModel.toUiModel() = UserUiModel(
    id = id,
    name = name
)

fun UserUiModel.toDomain() = UserModel(
    id = id,
    name = name
)
