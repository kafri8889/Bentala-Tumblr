package com.anafthdev.bentalatumblr.data.model.db

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.anafthdev.bentalatumblr.data.enums.TrackingMethod
import kotlinx.parcelize.Parcelize

@Parcelize
@Immutable
@Entity(tableName = "drink_history")
data class DrinkHistory(
	@PrimaryKey
	@ColumnInfo(name = "id") val id: Int,
	@ColumnInfo(name = "date") val date: Long,
	/**
	 * Represents the goal set for a specific date in the drink history.
	 * The goal indicates the target amount intended to be consumed on the specified date.
	 *
	 * @property goal volume in milliliter
	 */
	@ColumnInfo(name = "goal") val goal: Double,
	@ColumnInfo(name = "bottle") val bottle: DrinkBottle,
	@ColumnInfo(name = "tracking_method") val trackingMethod: TrackingMethod
): Parcelable
