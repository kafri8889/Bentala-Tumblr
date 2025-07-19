package com.anafthdev.bentalatumblr.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AccessoryItem(
    val id: String,
    val name: String,
    val price: Int,
    val point: Int,
    val imageUrl: String,

    val imageResId: Int,
): Parcelable
