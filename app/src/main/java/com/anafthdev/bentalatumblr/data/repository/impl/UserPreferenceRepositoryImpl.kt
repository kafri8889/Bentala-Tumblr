package com.anafthdev.bentalatumblr.data.repository.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import com.anafthdev.bentalatumblr.ProtoUserPreference
import com.anafthdev.bentalatumblr.data.model.UserPreference
import com.anafthdev.bentalatumblr.data.repository.UserPreferenceRepository
import com.anafthdev.bentalatumblr.foundation.extension.toProtoUserPreference
import com.anafthdev.bentalatumblr.foundation.extension.toUserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceRepositoryImpl @Inject constructor(
    private val userPreferenceDataStore: DataStore<ProtoUserPreference>
) : UserPreferenceRepository {

    override val getUserPreference: Flow<UserPreference>
        get() = userPreferenceDataStore.data.map { it.toUserPreference() }


	override suspend fun setUserPreference(userPreference: UserPreference) {
		userPreferenceDataStore.updateData {
			userPreference.toProtoUserPreference()
		}
	}

	companion object {
        val corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = {
				ProtoUserPreference(
					isFirstInstall = true,
					dailyGoal = 0.0
				)
			}
        )
    }

}
