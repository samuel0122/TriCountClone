package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.preferences.entities.LoggedUserPreference
import com.oliva.samuel.tricountclone.domain.model.LoggedUserModel
import com.oliva.samuel.tricountclone.ui.model.LoggedUserUiModel

fun LoggedUserPreference.toDomain() = LoggedUserModel(
    loggedUserId = loggedUserId
)

fun LoggedUserModel.toPreferences() = LoggedUserPreference(
    loggedUserId = loggedUserId
)

fun LoggedUserUiModel.toDomain() = LoggedUserModel(
    loggedUserId = loggedUserUiModel.id
)
