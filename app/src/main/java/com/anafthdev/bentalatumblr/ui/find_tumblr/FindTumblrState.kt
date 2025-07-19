package com.anafthdev.bentalatumblr.ui.find_tumblr

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class FindTumblrState(
    val tumblrName: String = "Geyora's Tumblr",
    val lastUpdate: Long = System.currentTimeMillis(),
    val locationText: String = "Jl.raya Susukan, Bojonggede, Bogor, Jawa Barat",
    val locationCoordinate: Pair<Double, Double> = Pair(-6.467790, 106.900820),
    val tumblrTemperature: Float = 22f,
    val tumblrBattery: Int = 85
): Parcelable
