package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.domain.model.Currency
import com.oliva.samuel.tricountclone.domain.model.TricountModel
import com.oliva.samuel.tricountclone.ui.model.TricountUiModel

fun TricountEntity.toDomain() = TricountModel(
    id = id,
    title = title,
    icon = icon,
    currency = Currency.fromSymbol(currency),
    createdBy = createdBy,
    createdAt = createdAt
)

fun TricountModel.toDatabase() = TricountEntity(
    id = id,
    title = title,
    icon = icon,
    currency = currency.symbol,
    createdBy = createdBy,
    createdAt = createdAt
)

fun TricountModel.toUiModel() = TricountUiModel(
    id = id,
    title = title,
    icon = icon,
    currency = currency,
    createdBy = createdBy,
    createdAt = createdAt
)

fun TricountUiModel.toDomain() = TricountModel(
    id = id,
    title = title,
    icon = icon,
    currency = currency,
    createdBy = createdBy,
    createdAt = createdAt
)
