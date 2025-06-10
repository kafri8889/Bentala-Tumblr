package com.anafthdev.bentalatumblr.ui.home

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class HomeState(
    val any: String = ""
): Parcelable
