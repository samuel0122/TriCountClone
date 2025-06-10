package com.oliva.samuel.tricountclone.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import com.oliva.samuel.tricountclone.data.database.dao.ExpenseDao
import com.oliva.samuel.tricountclone.data.database.dao.ExpenseShareDao
import com.oliva.samuel.tricountclone.data.database.dao.ParticipantDao
import com.oliva.samuel.tricountclone.data.database.dao.TricountDao
import com.oliva.samuel.tricountclone.data.database.dao.UserDao
import com.oliva.samuel.tricountclone.data.database.entities.ExpenseEntity
import com.oliva.samuel.tricountclone.data.database.entities.ExpenseShareEntity
import com.oliva.samuel.tricountclone.data.database.entities.ParticipantEntity
import com.oliva.samuel.tricountclone.data.database.entities.TricountEntity
import com.oliva.samuel.tricountclone.data.database.entities.UserEntity
import com.oliva.samuel.tricountclone.utils.converters.DateConverter
import com.oliva.samuel.tricountclone.utils.converters.UUIDConverter

@Database(
    version = TricountContract.DATABASE_VERSION,
    entities = [
        UserEntity::class,
        TricountEntity::class,
        ParticipantEntity::class,
        ExpenseEntity::class,
        ExpenseShareEntity::class
    ]
)
@TypeConverters(
    UUIDConverter::class,
    DateConverter::class
)
abstract class TricountDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun tricountDao(): TricountDao
    abstract fun participantDao(): ParticipantDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun expenseShareDao(): ExpenseShareDao
}
