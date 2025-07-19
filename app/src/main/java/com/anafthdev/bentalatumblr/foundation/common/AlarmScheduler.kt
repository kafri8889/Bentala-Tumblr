package com.anafthdev.bentalatumblr.foundation.common

import java.time.LocalDateTime

interface AlarmScheduler {

    /**
     * Schedule an alarm item
     *
     * @return `true` if alarm scheduled, false if permission not granted
     */
    fun schedule(alarmItem: AlarmItem): Boolean

    fun cancel(alarmItem: AlarmItem)
}

data class AlarmItem(
    val id: Int,
    val message: String,
    val alarmTime: LocalDateTime
)
