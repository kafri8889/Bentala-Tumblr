package com.anafthdev.bentalatumblr.ui.home

import androidx.lifecycle.SavedStateHandle
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<HomeState>(
    savedStateHandle = savedStateHandle,
    defaultState = HomeState()
) {

}
