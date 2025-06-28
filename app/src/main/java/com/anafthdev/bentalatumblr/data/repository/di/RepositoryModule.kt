package com.anafthdev.bentalatumblr.data.repository.di

import androidx.datastore.core.DataStore
import com.anafthdev.bentalatumblr.ProtoUserPreference
import com.anafthdev.bentalatumblr.data.datasource.local.dao.DrinkBottleDao
import com.anafthdev.bentalatumblr.data.datasource.local.dao.DrinkHistoryDao
import com.anafthdev.bentalatumblr.data.datasource.local.dao.ReminderDao
import com.anafthdev.bentalatumblr.data.repository.DrinkBottleRepository
import com.anafthdev.bentalatumblr.data.repository.DrinkHistoryRepository
import com.anafthdev.bentalatumblr.data.repository.ReminderRepository
import com.anafthdev.bentalatumblr.data.repository.UserPreferenceRepository
import com.anafthdev.bentalatumblr.data.repository.impl.DrinkBottleRepositoryImpl
import com.anafthdev.bentalatumblr.data.repository.impl.DrinkHistoryRepositoryImpl
import com.anafthdev.bentalatumblr.data.repository.impl.ReminderRepositoryImpl
import com.anafthdev.bentalatumblr.data.repository.impl.UserPreferenceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserPreferenceRepository(
        userPreferenceDatastore: DataStore<ProtoUserPreference>
    ): UserPreferenceRepository = UserPreferenceRepositoryImpl(userPreferenceDatastore)

    @Provides
    @Singleton
    fun provideDrinkBottleRepository(
        drinkBottleDao: DrinkBottleDao
    ): DrinkBottleRepository = DrinkBottleRepositoryImpl(drinkBottleDao)

	@Provides
    @Singleton
    fun provideDrinkHistoryRepository(
        drinkHistoryDao: DrinkHistoryDao
    ): DrinkHistoryRepository = DrinkHistoryRepositoryImpl(drinkHistoryDao)

	@Provides
	@Singleton
	fun provideReminderRepository(
		reminderDao: ReminderDao
	): ReminderRepository = ReminderRepositoryImpl(reminderDao)

}
