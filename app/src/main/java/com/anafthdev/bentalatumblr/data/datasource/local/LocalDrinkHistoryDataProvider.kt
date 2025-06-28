package com.anafthdev.bentalatumblr.data.datasource.local

import com.anafthdev.bentalatumblr.data.enums.TrackingMethod
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.random.Random

object LocalDrinkHistoryDataProvider {

	private val dateFormatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())

	val history1 = DrinkHistory(
		id = 0,
		date = dateFormatter.parse("01/01/2024 00:01:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle550Ml,
		goal = 1000.0,
		trackingMethod = TrackingMethod.Manual
	)
	
	val history2 = DrinkHistory(
		id = 1,
		date = dateFormatter.parse("01/01/2024 00:02:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle175Ml,
		goal = 1100.0,
		trackingMethod = TrackingMethod.Auto
	)
	
	val history3 = DrinkHistory(
		id = 2,
		date = dateFormatter.parse("02/01/2024 00:03:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle225Ml,
		goal = 1200.0,
		trackingMethod = TrackingMethod.Manual
	)

	val history4 = DrinkHistory(
		id = 3,
		date = dateFormatter.parse("02/01/2024 00:04:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle400Ml,
		goal = 1300.0,
		trackingMethod = TrackingMethod.Auto
	)

	val history5 = DrinkHistory(
		id = 4,
		date = dateFormatter.parse("08/01/2024 00:05:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle125Ml,
		goal = 1400.0,
		trackingMethod = TrackingMethod.Manual
	)

	val history6 = DrinkHistory(
		id = 5,
		date = dateFormatter.parse("08/01/2024 00:06:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle300Ml,
		goal = 1500.0,
		trackingMethod = TrackingMethod.Auto
	)

	val history7 = DrinkHistory(
		id = 6,
		date = dateFormatter.parse("01/02/2024 00:07:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle300Ml,
		goal = 1600.0,
		trackingMethod = TrackingMethod.Manual
	)

	val history8 = DrinkHistory(
		id = 7,
		date = dateFormatter.parse("02/02/2024 00:08:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle550Ml,
		goal = 1700.0,
		trackingMethod = TrackingMethod.Auto
	)

	val history9 = DrinkHistory(
		id = 8,
		date = dateFormatter.parse("01/01/2025 00:09:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle125Ml,
		goal = 1800.0,
		trackingMethod = TrackingMethod.Manual
	)

	val history10 = DrinkHistory(
		id = 9,
		date = dateFormatter.parse("01/05/2024 00:10:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle550Ml,
		goal = 1900.0,
		trackingMethod = TrackingMethod.Auto
	)

	val history11 = DrinkHistory(
		id = 10,
		date = dateFormatter.parse("24/04/2024 00:11:00")!!.time,
		bottle = LocalDrinkBottleDataProvider.drinkBottle225Ml,
		goal = 2000.0,
		trackingMethod = TrackingMethod.Manual
	)
	
	val values = arrayOf(
		history1,
		history2,
		history3,
		history4,
		history5,
		history6,
		history7,
		history8,
		history9,
		history10,
		history11,
	)

	/**
	 * Function to add histories. Each month contains two history
	 * @param year the year
	 */
	fun addHistoriesForYear(year: Int): List<DrinkHistory> {
		return arrayListOf<DrinkHistory>().apply {
			val calendar = Calendar.getInstance()
			calendar.set(year, Calendar.JANUARY, 1)

			while (calendar.get(Calendar.YEAR) == year) {
				val month = calendar.get(Calendar.MONTH)
				addAll(addMonthlyHistories(calendar.time, month))
				calendar.add(Calendar.MONTH, 1)
			}
		}
	}

	/**
	 * Function to add histories for a specific month
	 * @param month from 1 - 12
	 */
	fun addMonthlyHistories(date: Date, month: Int): List<DrinkHistory> {
		return arrayListOf<DrinkHistory>().apply {
			val calendar = Calendar.getInstance()
			calendar.time = date
			val numberOfDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

			for (i in 1..numberOfDays step 15) {
				val day = if (i < 10) "0$i" else "$i"
				val hour = Random.nextInt(0, 23).let {
					if (it < 10) "0$it" else "$it"
				}
				val minute = Random.nextInt(0, 59).let {
					if (it < 10) "0$it" else "$it"
				}
				val second = Random.nextInt(0, 59).let {
					if (it < 10) "0$it" else "$it"
				}

				val history = DrinkHistory(
					id = Random.nextInt(),
					date = dateFormatter.parse("$day/${month + 1}/${calendar.get(Calendar.YEAR)} $hour:$minute:$second")!!.time,
					bottle = LocalDrinkBottleDataProvider.getRandomDefaultBottle(),
					goal = Random.nextDouble(1000.0, 2000.0), // Random goal between 1000 and 2000
					trackingMethod = TrackingMethod.entries.random()
				)

				add(history)
			}
		}
	}
	
}
