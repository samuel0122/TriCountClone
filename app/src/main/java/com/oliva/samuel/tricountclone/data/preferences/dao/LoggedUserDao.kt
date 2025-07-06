package com.oliva.samuel.tricountclone.data.preferences.dao

import com.oliva.samuel.tricountclone.data.preferences.entities.LoggedUserPreference
import kotlinx.coroutines.flow.Flow

interface LoggedUserDao {
    suspend fun setLoggedUserId(userId: LoggedUserPreference)
    suspend fun getLoggedUserId(): LoggedUserPreference
    fun getLoggedUserIdFlow(): Flow<LoggedUserPreference>
}
