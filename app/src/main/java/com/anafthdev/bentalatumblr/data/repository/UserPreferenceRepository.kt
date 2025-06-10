package com.anafthdev.bentalatumblr.data.repository

import com.anafthdev.bentalatumblr.data.model.UserPreference
import kotlinx.coroutines.flow.Flow

interface UserPreferenceRepository {

	val getUserPreference: Flow<UserPreference>

	suspend fun setUserPreference(userPreference: UserPreference)

}
