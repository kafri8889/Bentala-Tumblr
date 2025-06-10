package com.anafthdev.bentalatumblr.data.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
data class UserPreference(
	val isFirstInstall: Boolean = false,
	val dailyGoal: Double = 0.0,
) : Parcelable
