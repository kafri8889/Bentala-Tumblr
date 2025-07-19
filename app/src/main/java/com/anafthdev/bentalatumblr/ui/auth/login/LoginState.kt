package com.anafthdev.bentalatumblr.ui.auth.login

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class LoginState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isRememberMe: Boolean = false,
    val isLoading: Boolean = false,
): Parcelable
