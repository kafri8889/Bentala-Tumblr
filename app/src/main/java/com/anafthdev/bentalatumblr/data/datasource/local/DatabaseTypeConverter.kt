package com.anafthdev.bentalatumblr.data.datasource.local

import androidx.room.TypeConverter
import com.anafthdev.bentalatumblr.data.enums.TrackingMethod
import com.anafthdev.bentalatumblr.data.model.db.DrinkBottle
import com.google.gson.Gson

object DatabaseTypeConverter {

	@TypeConverter
	fun drinkBottleToJson(drinkBottle: DrinkBottle): String = Gson().toJson(drinkBottle)

	@TypeConverter
	fun drinkBottleFromJson(json: String): DrinkBottle = Gson().fromJson(json, DrinkBottle::class.java)

	@TypeConverter
	fun trackingMethodToOrdinal(trackingMethod: TrackingMethod): Int = trackingMethod.ordinal

	@TypeConverter
	fun trackingMethodFromOrdinal(ordinal: Int): TrackingMethod = TrackingMethod.entries[ordinal]

}
