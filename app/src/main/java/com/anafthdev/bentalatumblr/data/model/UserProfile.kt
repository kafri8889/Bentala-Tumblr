package com.anafthdev.bentalatumblr.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserProfile(
    val name: String,
    val username: String,
    val avatarUrl: String,

    val tumblrName: String,
    val isPremium: Boolean
): Parcelable {

    val isLoggedIn: Boolean
        get() = name.isNotEmpty() && username.isNotEmpty()

}
