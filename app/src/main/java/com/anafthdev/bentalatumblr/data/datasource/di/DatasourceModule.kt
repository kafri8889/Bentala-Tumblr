package com.anafthdev.bentalatumblr.data.datasource.di

import android.content.Context
import com.anafthdev.bentalatumblr.data.datasource.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = AppDatabase.getInstance(context)

    @Provides
    @Singleton
    fun provideDrinkHistoryDao(
        appDatabase: AppDatabase
    ) = appDatabase.drinkHistoryDao()

    @Provides
    @Singleton
    fun provideDrinkBottleDao(
        appDatabase: AppDatabase
    ) = appDatabase.drinkBottleDao()

    @Provides
    @Singleton
    fun provideReminderDao(
        appDatabase: AppDatabase
    ) = appDatabase.reminderDao()

}
