package com.oliva.samuel.tricountclone.domain.model

data class UserWithCreatedTricountsItem(
    val user: UserItem,
    val tricounts: List<TricountItem>
)
