package com.oliva.samuel.tricountclone.data.preferences.contracts

import androidx.datastore.preferences.core.stringPreferencesKey

object PreferencesContract {

    const val LOGGED_USER_PREFERENCES_NAME = "logged_user_preferences"
    object KEYS {
        val LOGGED_USER_ID_KEY = stringPreferencesKey("logged_user_id")
    }
}
