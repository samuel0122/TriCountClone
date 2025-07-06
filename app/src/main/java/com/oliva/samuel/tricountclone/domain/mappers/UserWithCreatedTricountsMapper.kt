package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.relations.UserWithCreatedTricountsRelation
import com.oliva.samuel.tricountclone.domain.model.UserWithCreatedTricountsModel

fun UserWithCreatedTricountsRelation.toDomain() =
    UserWithCreatedTricountsModel(
        user = user.toDomain(),
        tricounts = tricounts.map { it.toDomain() }
    )

fun UserWithCreatedTricountsModel.toDatabase() =
    UserWithCreatedTricountsRelation(
        user = user.toDatabase(),
        tricounts = tricounts.map { it.toDatabase() }
    )
