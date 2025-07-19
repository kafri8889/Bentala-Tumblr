package com.anafthdev.bentalatumblr.ui.auth.onboarding

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class OnboardingState(
    val any: String = ""
): Parcelable
