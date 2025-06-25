package com.oliva.samuel.tricountclone.domain.model

import java.util.Date
import java.util.UUID

data class TricountItem(
    val id: UUID,
    val title: String,
    val icon: String,
    val currency: String,
    val createdBy: UUID,
    val createdAt: Date
)
