package com.anafthdev.bentalatumblr.ui.marketplace

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.anafthdev.bentalatumblr.data.model.AccessoryItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class MarketplaceState(
    val point: Int = 22,
    val items: ImmutableList<AccessoryItem> = persistentListOf()
): Parcelable
