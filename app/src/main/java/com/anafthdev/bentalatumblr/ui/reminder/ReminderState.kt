package com.anafthdev.bentalatumblr.ui.reminder

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import com.anafthdev.bentalatumblr.data.model.db.Reminder
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
data class ReminderState(
    val reminders: List<Reminder> = emptyList()
): Parcelable
