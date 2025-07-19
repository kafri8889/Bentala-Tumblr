package com.anafthdev.bentalatumblr.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.anafthdev.bentalatumblr.data.enums.TrackingMethod
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.foundation.base.datasource.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DrinkHistoryDao: BaseDao<DrinkHistory> {

	@Query("SELECT * FROM drink_history")
	@Transaction
	abstract fun getAll(): Flow<List<DrinkHistory>>

	@Query("SELECT * FROM drink_history WHERE :id LIKE id")
	abstract fun getById(id: Int): Flow<DrinkHistory?>

	@Query(
		"SELECT * FROM drink_history WHERE " +
				"strftime('%d%m%Y', datetime(date / 1000, 'unixepoch')) = " +
				"strftime('%d%m%Y', datetime(:timeInMillis / 1000, 'unixepoch'))"
	)
	abstract fun getDaily(timeInMillis: Long): Flow<List<DrinkHistory>>

	@Query("SELECT * FROM drink_history WHERE date BETWEEN :start AND :end")
	abstract fun getByDateRange(start: Long, end: Long): Flow<List<DrinkHistory>>

	@Query("SELECT * FROM drink_history WHERE date = (SELECT MIN(date) FROM drink_history)")
	abstract fun getByEarliestDate(): Flow<DrinkHistory?>

	@Query("SELECT * FROM drink_history WHERE date = (SELECT MAX(date) FROM drink_history)")
	abstract fun getByLatestDate(): Flow<DrinkHistory?>

	@Query("SELECT * FROM drink_history WHERE tracking_method = :trackingMethod")
	abstract fun getByTrackingMethod(trackingMethod: TrackingMethod): Flow<List<DrinkHistory>>

}
