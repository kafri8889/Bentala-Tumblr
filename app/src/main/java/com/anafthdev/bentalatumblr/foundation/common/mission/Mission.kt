package com.anafthdev.bentalatumblr.foundation.common.mission

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import java.time.Instant
import java.time.ZoneId

interface Mission {
    val id: String
    @get:StringRes
    val title: Int
    @get:StringRes
    val description: Int
    @get:DrawableRes
    val icon: Int

    /**
     * Dipanggil ketika drink history baru ditambahkan (user minum air)
     */
    fun onDrink(history: DrinkHistory): MissionResult

    interface MissionResult {
        val isValid: Boolean

        fun apply(progress: MissionProgress): MissionProgress
    }

    class MissionResultImpl(
        override val isValid: Boolean
    ): MissionResult {
        override fun apply(progress: MissionProgress): MissionProgress {
            if (!isValid) return progress

            return progress.copy(
                progress = 1f
            )
        }
    }
}

/**
 * Time based achievement (00:00 - 23:59)
 *
 * @property timeType Before or After
 * @property hours 24 hours (0-23)
 * @property minutes minutes (0-59)
 */
class TimeBasedMission(
    override val id: String,
    override val title: Int,
    override val description: Int,
    override val icon: Int,
    val timeType: TimeType,
    val hours: Int,
    val minutes: Int,
): Mission {

    enum class TimeType {
        Before,
        After
    }
    
    override fun onDrink(history: DrinkHistory): Mission.MissionResult {
        val instant = Instant.ofEpochMilli(history.date)
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())
        val targetTime = zonedDateTime
            .withHour(hours)
            .withMinute(minutes)

        if (timeType == TimeType.Before) {
            if (zonedDateTime.isBefore(targetTime)) {
                return Mission.MissionResultImpl(true)
            }
        } else {
            if (zonedDateTime.isAfter(targetTime)) {
                return Mission.MissionResultImpl(true)
            }
        }

        return Mission.MissionResultImpl(false)
    }

}
