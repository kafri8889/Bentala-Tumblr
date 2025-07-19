package com.anafthdev.bentalatumblr.ui.auth.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.anafthdev.bentalatumblr.data.model.UserPreference
import com.anafthdev.bentalatumblr.data.model.UserProfile
import com.anafthdev.bentalatumblr.data.repository.UserPreferenceRepository
import com.anafthdev.bentalatumblr.data.repository.UserProfileRepository
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val userProfileRepository: UserProfileRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel<LoginState>(
    savedStateHandle = savedStateHandle,
    defaultState = LoginState()
) {

    fun setEmail(email: String) {
        updateState {
            copy(
                email = email
            )
        }
    }

    fun setPassword(password: String) {
        updateState {
            copy(
                password = password
            )
        }
    }

    fun setIsPasswordVisible(isPasswordVisible: Boolean) {
        updateState {
            copy(
                isPasswordVisible = isPasswordVisible
            )
        }
    }

    fun setIsLoading(isLoading: Boolean) {
        updateState {
            copy(
                isLoading = isLoading
            )
        }
    }

    fun setIsRememberMe(isRememberMe: Boolean) {
        updateState {
            copy(
                isRememberMe = isRememberMe
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            userPreferenceRepository.setUserPreference(
                UserPreference(
                    isFirstInstall = false,
                    dailyGoal = 1200.0
                )
            )

            userProfileRepository.setUserProfile(
                UserProfile(
                    name = "Dina Sartika",
                    username = "Dina Sartika",
                    avatarUrl = "",
                    tumblrName = "Geyora",
                    isPremium = false
                )
            )
//            delay(2000)
            sendEvent(LoginEvent.LoggedIn)
        }
    }

}
