package com.oliva.samuel.tricountclone.domain.mappers

import com.oliva.samuel.tricountclone.data.preferences.entities.LoggedUserPreference
import com.oliva.samuel.tricountclone.domain.model.LoggedUserModel

fun LoggedUserPreference.toDomain() = LoggedUserModel(
    loggedUserId = loggedUserId
)

fun LoggedUserModel.toPreferences() = LoggedUserPreference(
    loggedUserId = loggedUserId
)
