package com.anafthdev.bentalatumblr.ui.mission

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Immutable
@Serializable
@Parcelize
data class MissionState(
    val missionProgress: List<MissionProgress> = emptyList(),
    val unlockedAchievements: Int = 0
): Parcelable
