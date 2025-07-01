package com.anafthdev.bentalatumblr.foundation.common.mission

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MissionModule {

    @Provides
    @Singleton
    fun provideMissionManager(): MissionManager {
        return MissionManager()
    }

}
