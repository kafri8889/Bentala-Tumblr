package com.anafthdev.bentalatumblr.ui.find_tumblr

import androidx.lifecycle.SavedStateHandle
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FindTumblrViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<FindTumblrState>(
    savedStateHandle = savedStateHandle,
    defaultState = FindTumblrState()
) {

}
