package com.anafthdev.bentalatumblr.ui.marketplace

import androidx.lifecycle.SavedStateHandle
import com.anafthdev.bentalatumblr.data.datasource.local.LocalAccessoryItemDataProvider
import com.anafthdev.bentalatumblr.foundation.base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
): BaseViewModel<MarketplaceState>(
    savedStateHandle = savedStateHandle,
    defaultState = MarketplaceState()
) {
    init {
        updateState {
            copy(
                items = LocalAccessoryItemDataProvider.values.toImmutableList()
            )
        }
    }
}
