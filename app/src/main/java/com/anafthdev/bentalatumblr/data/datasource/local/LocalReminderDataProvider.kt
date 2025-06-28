package com.anafthdev.bentalatumblr.data.datasource.local

import com.anafthdev.bentalatumblr.data.model.db.Reminder

object LocalReminderDataProvider {

	val reminder1 = Reminder(
		id = 1,
		hour = 13,
		minute = 0,
		isActive = true
	)

	val reminder2 = Reminder(
		id = 2,
		hour = 2,
		minute = 30,
		isActive = false
	)

	val reminder3 = Reminder(
		id = 3,
		hour = 15,
		minute = 30,
		isActive = true,
		drinkBottle = LocalDrinkBottleDataProvider.drinkBottle175Ml
	)

}
