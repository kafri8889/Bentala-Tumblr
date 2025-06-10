package com.anafthdev.bentalatumblr.foundation.extension

import com.anafthdev.bentalatumblr.ProtoUserPreference
import com.anafthdev.bentalatumblr.data.model.UserPreference

fun ProtoUserPreference.toUserPreference(): UserPreference = UserPreference(
	isFirstInstall = isFirstInstall,
	dailyGoal = dailyGoal,
)

fun UserPreference.toProtoUserPreference(): ProtoUserPreference = ProtoUserPreference(
	isFirstInstall = isFirstInstall,
	dailyGoal = dailyGoal,
)
