package com.anafthdev.bentalatumblr.ui.reminder

import androidx.lifecycle.SavedStateHandle
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<ReminderState>(
    savedStateHandle = savedStateHandle,
    defaultState = ReminderState()
) {

}
