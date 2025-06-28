package com.anafthdev.bentalatumblr.data.repository

import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import kotlinx.coroutines.flow.Flow

interface DrinkHistoryRepository {

	fun getAll(): Flow<List<DrinkHistory>>

	fun getById(id: Int): Flow<DrinkHistory?>

	/**
	 * Get daily drink history, e.g date 25-02-2024, but in milliseconds
	 */
	fun getDaily(timeInMillis: Long): Flow<List<DrinkHistory>>

	/**
	 * Get daily drink history by date rang, e.g from 25-02-2024 until 27-02-2024, but in milliseconds
	 *
	 * @param start start date
	 * @param end end date (inclusive)
	 */
	fun getByDateRange(start: Long, end: Long): Flow<List<DrinkHistory>>

	/**
	 * Retrieves the earliest drink history entry recorded.
	 */
	fun getByEarliestDate(): Flow<DrinkHistory?>

	/**
	 * Retrieves the latest drink history entry recorded.
	 */
	fun getByLatestDate(): Flow<DrinkHistory?>

	suspend fun insert(vararg t: DrinkHistory)

	suspend fun insert(ts: Collection<DrinkHistory>)

	suspend fun update(vararg t: DrinkHistory)

	suspend fun update(ts: Collection<DrinkHistory>)

	suspend fun delete(vararg t: DrinkHistory)

	suspend fun delete(ts: Collection<DrinkHistory>)

}
