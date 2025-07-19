package com.anafthdev.bentalatumblr.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.anafthdev.bentalatumblr.ProtoUserPreference
import com.anafthdev.bentalatumblr.ProtoUserProfile
import com.anafthdev.bentalatumblr.data.Constant
import com.anafthdev.bentalatumblr.data.repository.impl.UserPreferenceRepositoryImpl
import com.anafthdev.bentalatumblr.data.repository.impl.UserProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatastoreModule {

    @Provides
    @Singleton
    fun provideUserPreferenceDataStore(
        @ApplicationContext context: Context,
    ): DataStore<ProtoUserPreference> = DataStoreFactory.create(
        serializer = UserPreferenceSerializer,
        corruptionHandler = UserPreferenceRepositoryImpl.corruptionHandler,
        produceFile = { context.dataStoreFile(Constant.DATASTORE_USER_PREFERENCE) }
    )

    @Provides
    @Singleton
    fun provideUserProfileDataStore(
        @ApplicationContext context: Context,
    ): DataStore<ProtoUserProfile> = DataStoreFactory.create(
        serializer = UserProfileSerializer,
        corruptionHandler = UserProfileRepositoryImpl.corruptionHandler,
        produceFile = { context.dataStoreFile(Constant.DATASTORE_USER_PROFILE) }
    )

}
