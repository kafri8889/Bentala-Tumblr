package com.anafthdev.bentalatumblr.ui.auth.onboarding

import androidx.lifecycle.SavedStateHandle
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<OnboardingState>(
    savedStateHandle = savedStateHandle,
    defaultState = OnboardingState()
) {

}
