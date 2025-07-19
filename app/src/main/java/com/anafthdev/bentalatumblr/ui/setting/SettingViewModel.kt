package com.anafthdev.bentalatumblr.ui.setting

import androidx.lifecycle.SavedStateHandle
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<SettingState>(
    savedStateHandle = savedStateHandle,
    defaultState = SettingState()
) {



}
