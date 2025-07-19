package com.anafthdev.bentalatumblr.foundation.extension

import com.anafthdev.bentalatumblr.ProtoUserProfile
import com.anafthdev.bentalatumblr.data.model.UserProfile

fun ProtoUserProfile.toUserProfile(): UserProfile = UserProfile(
    name = name,
    username = username,
    avatarUrl = avatarUrl,

    tumblrName = tumblrName,
    isPremium = isPremium
)

fun UserProfile.toProtoUserProfile(): ProtoUserProfile = ProtoUserProfile(
    name = name,
    username = username,
    avatarUrl = avatarUrl,
    tumblrName = tumblrName,
    isPremium = isPremium
)
