package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.data.database.dao.UserDao
import com.oliva.samuel.tricountclone.data.preferences.dao.LoggedUserDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.mappers.toPreferences
import com.oliva.samuel.tricountclone.domain.model.LoggedUserModel
import com.oliva.samuel.tricountclone.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val loggedUserDao: LoggedUserDao
) {
    fun getUsers(): Flow<List<UserModel>> = userDao
        .getAllFlow()
        .map { userEntities ->
            userEntities.map { it.toDomain() }
        }

    fun getUserFlow(userId: UserId): Flow<UserModel> = userDao
        .getUserFlow(userId)
        .map { userEntity ->
            userEntity.toDomain()
        }

    suspend fun getUser(id: UserId): UserModel? =
        userDao.getUser(id)?.toDomain()

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
