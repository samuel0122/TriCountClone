package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.data.database.dao.UserDao
import com.oliva.samuel.tricountclone.data.preferences.dao.LoggedUserDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.mappers.toPreferences
import com.oliva.samuel.tricountclone.domain.model.LoggedUserModel
import com.oliva.samuel.tricountclone.domain.model.UserModel
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val loggedUserDao: LoggedUserDao
) {
    fun getUsers() = userDao
        .getAll()
        .map { userEntities ->
            userEntities.map { it.toDomain() }
        }

    fun getUserWithCreatedTricounts(id: UUID) = userDao
        .getUserWithCreatedTricounts(id)
        .map {
            it.toDomain()
        }

    suspend fun getUser(id: UUID) = userDao.getUser(id)

    suspend fun setLoggedUser(user: LoggedUserModel) {
        loggedUserDao.setLoggedUserId(user.toPreferences())
    }

    suspend fun getLoggedUser(): UserModel? {
        val loggedUser = loggedUserDao.getLoggedUserId()

        return loggedUser.loggedUserId?.let { userId ->
            userDao.getUser(userId)?.toDomain()
        }
    }

    suspend fun insertUser(user: UserModel) {
        userDao.insert(user.toDatabase())
    }

    suspend fun updateUser(user: UserModel) {
        userDao.update(user.toDatabase())
    }

    suspend fun deleteUser(user: UserModel) {
        userDao.delete(user.toDatabase())
    }
}
