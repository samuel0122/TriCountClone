package com.oliva.samuel.tricountclone.domain.model

data class UserWithCreatedTricountsModel(
    val user: UserModel,
    val tricounts: List<TricountModel>
)
