package com.oliva.samuel.tricountclone.data.preferences.dao

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.oliva.samuel.tricountclone.data.preferences.contracts.PreferencesContract
import com.oliva.samuel.tricountclone.data.preferences.entities.LoggedUserPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import java.util.UUID
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(
    name = PreferencesContract.LOGGED_USER_PREFERENCES_NAME
)

class DataStoreLoggedUserDao @Inject constructor(
    @ApplicationContext private val context: Context
) : LoggedUserDao {
    override suspend fun setLoggedUserId(userId: LoggedUserPreference) {
        context.dataStore.edit { preferences ->
            if (userId.loggedUserId != null) {
                preferences[PreferencesContract.KEYS.LOGGED_USER_ID_KEY] =
                    userId.loggedUserId.toString()
            } else {
                preferences.remove(PreferencesContract.KEYS.LOGGED_USER_ID_KEY)
            }
        }
    }

    override suspend fun getLoggedUserId(): LoggedUserPreference {
        val preferences = context.dataStore.data.first()
        return LoggedUserPreference(
            loggedUserId = preferences[PreferencesContract.KEYS.LOGGED_USER_ID_KEY]?.let {
                UUID.fromString(it)
            })
    }

    override fun getLoggedUserIdFlow(): Flow<LoggedUserPreference> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) emit(emptyPreferences())
                else throw exception
            }
            .map { preferences ->
                LoggedUserPreference(
                    loggedUserId = preferences[PreferencesContract.KEYS.LOGGED_USER_ID_KEY]?.let {
                        UUID.fromString(it)
                    })
            }

}
