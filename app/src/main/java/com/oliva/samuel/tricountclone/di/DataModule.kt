package com.oliva.samuel.tricountclone.di

import com.oliva.samuel.tricountclone.data.preferences.dao.DataStoreLoggedUserDao
import com.oliva.samuel.tricountclone.data.preferences.dao.LoggedUserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideLoggedUserPreferencesDao(
        dataStoreLoggedUserDao: DataStoreLoggedUserDao
    ): LoggedUserDao = dataStoreLoggedUserDao

}
