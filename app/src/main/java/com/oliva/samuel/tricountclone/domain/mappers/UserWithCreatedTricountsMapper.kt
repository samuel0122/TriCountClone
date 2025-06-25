package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.relations.UserWithCreatedTricountsRelation
import com.oliva.samuel.tricountclone.domain.model.UserWithCreatedTricountsItem

fun UserWithCreatedTricountsRelation.toDomain() =
    UserWithCreatedTricountsItem(
        user = user.toDomain(),
        tricounts = tricounts.map { it.toDomain() }
    )

fun UserWithCreatedTricountsItem.toDatabase() =
    UserWithCreatedTricountsRelation(
        user = user.toDatabase(),
        tricounts = tricounts.map { it.toDatabase() }
    )
