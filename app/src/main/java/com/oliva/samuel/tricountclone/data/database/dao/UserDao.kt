package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.oliva.samuel.tricountclone.core.UserId
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.UserEntity
import com.oliva.samuel.tricountclone.data.database.entities.relations.UserWithCreatedTricountsRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM ${TricountContract.TABLE_USERS}")
    fun getAllFlow(): Flow<List<UserEntity>>

    @Query(
        "SELECT * FROM ${TricountContract.TABLE_USERS} " +
                "WHERE ${TricountContract.TABLE_USERS_COLUMN_ID} = :userId"
    )
    fun getUserFlow(userId: UserId): Flow<UserEntity>

    @Query(
        "SELECT * FROM ${TricountContract.TABLE_USERS} " +
                "WHERE ${TricountContract.TABLE_USERS_COLUMN_ID} = :id"
    )
    suspend fun getUser(id: UserId): UserEntity?

    @Transaction
    @Query(
        "SELECT * FROM ${TricountContract.TABLE_USERS} " +
                "WHERE ${TricountContract.TABLE_USERS_COLUMN_ID} = :id"
    )
    fun getUserWithCreatedTricountsFlow(id: UserId): Flow<UserWithCreatedTricountsRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Update
    suspend fun update(user: UserEntity): Int

    @Delete
    suspend fun delete(user: UserEntity): Int
}
