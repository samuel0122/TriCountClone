package com.oliva.samuel.tricountclone.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity

@Dao
interface TricountDao {
    @Query("SELECT * FROM ${TricountContract.TABLE_TRICOUNT}")
    fun getAll(): List<TricountEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: TricountEntity): Long

    @Update
    suspend fun update(user: TricountEntity): Int

    @Delete
    suspend fun delete(user: TricountEntity): Int
}
