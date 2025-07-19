package com.anafthdev.bentalatumblr.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.anafthdev.bentalatumblr.ProtoUserProfile
import com.anafthdev.bentalatumblr.data.model.UserProfile
import com.anafthdev.bentalatumblr.data.repository.UserProfileRepository
import com.anafthdev.bentalatumblr.foundation.extension.toProtoUserProfile
import com.anafthdev.bentalatumblr.foundation.extension.toUserProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDataStore: DataStore<ProtoUserProfile>
) : UserProfileRepository {

    override val getUserProfile: Flow<UserProfile>
        get() = userProfileDataStore.data.map { it.toUserProfile() }


	override suspend fun setUserProfile(userProfile: UserProfile) {
		userProfileDataStore.updateData {
			userProfile.toProtoUserProfile()
		}
	}

	companion object {
        val corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = {
				ProtoUserProfile()
			}
        )
    }

}
