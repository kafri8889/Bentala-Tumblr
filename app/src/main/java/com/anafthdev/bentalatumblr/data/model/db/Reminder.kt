package com.anafthdev.bentalatumblr.data.model.db

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Immutable
@Parcelize
@Entity(tableName = "reminder")
data class Reminder(
	@PrimaryKey
	@ColumnInfo(name = "id_reminder") val id: Short,
	@ColumnInfo(name = "hour_reminder") val hour: Byte,
	@ColumnInfo(name = "minute_reminder") val minute: Byte,
	@ColumnInfo(name = "isActive_reminder") val isActive: Boolean,
	@ColumnInfo(name = "drinkBottle_reminder") val drinkBottle: DrinkBottle? = null,
): Parcelable
