package com.anafthdev.bentalatumblr

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anafthdev.bentalatumblr.data.repository.UserPreferenceRepository
import com.anafthdev.bentalatumblr.data.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userPreferenceRepository: UserPreferenceRepository,
    private val userProfileRepository: UserProfileRepository
): ViewModel() {

    var isLoggedIn by mutableStateOf<Boolean?>(null)
        private set

    var isFirstInstall by mutableStateOf<Boolean?>(null)
        private set

    init {
        viewModelScope.launch {
            userPreferenceRepository.getUserPreference.collect { profile ->
                isFirstInstall = profile.isFirstInstall
            }
        }

        viewModelScope.launch {
            userProfileRepository.getUserProfile.collect { profile ->
                isLoggedIn = profile.isLoggedIn
            }
        }
    }

}
