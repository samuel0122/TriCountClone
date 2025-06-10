package com.oliva.samuel.tricountclone.di

import android.content.Context
import androidx.room.Room
import com.oliva.samuel.tricountclone.data.database.TricountDatabase
import com.oliva.samuel.tricountclone.data.database.contracts.TricountContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, TricountDatabase::class.java, TricountContract.DATABASE_NAME)
        .fallbackToDestructiveMigration(true)
        .build()

    @Singleton
    @Provides
    fun provideUserDao(db: TricountDatabase) = db.userDao()

    @Singleton
    @Provides
    fun provideTricountDao(db: TricountDatabase) = db.tricountDao()

    @Singleton
    @Provides
    fun provideParticipant(db: TricountDatabase) = db.participantDao()

    @Singleton
    @Provides
    fun provideExpense(db: TricountDatabase) = db.expenseDao()

    @Singleton
    @Provides
    fun provideExpenseShare(db: TricountDatabase) = db.expenseShareDao()
}
