package com.anafthdev.bentalatumblr.ui.setting

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class SettingState(
    val any: String = ""
): Parcelable
