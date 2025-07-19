package com.anafthdev.bentalatumblr.foundation.common

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    @Singleton
    fun provideAndroidAlarmScheduler(
        @ApplicationContext context: Context
    ): AlarmScheduler = AndroidAlarmScheduler(context)

}
