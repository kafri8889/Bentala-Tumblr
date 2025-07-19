package com.anafthdev.bentalatumblr.data.repository

import com.anafthdev.bentalatumblr.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

	val getUserProfile: Flow<UserProfile>

	suspend fun setUserProfile(userProfile: UserProfile)

}
