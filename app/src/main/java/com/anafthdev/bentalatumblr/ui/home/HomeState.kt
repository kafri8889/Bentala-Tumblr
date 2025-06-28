package com.anafthdev.bentalatumblr.ui.home

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class HomeState(
    val name: String = "Ju Jingyi",
    val dayStreak: Int = 4,
    val bottleTemperature: Float = 20f,
    val bottleBattery: Float = 73f,
    val battery: Int = 73,
    val drinkProgress: Int = 800,
    val dailyGoal: Int = 1200,
    val drinkHistories: List<DrinkHistory> = emptyList(),
): Parcelable
