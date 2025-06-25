package com.oliva.samuel.tricountclone.data

import com.oliva.samuel.tricountclone.data.database.dao.UserDao
import com.oliva.samuel.tricountclone.domain.mappers.toDatabase
import com.oliva.samuel.tricountclone.domain.mappers.toDomain
import com.oliva.samuel.tricountclone.domain.model.UserItem
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao
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

    suspend fun insertUser(user: UserItem) {
        userDao.insert(user.toDatabase())
    }

    suspend fun updateUser(user: UserItem) {
        userDao.update(user.toDatabase())
    }

    suspend fun deleteUser(user: UserItem) {
        userDao.delete(user.toDatabase())
    }
}
