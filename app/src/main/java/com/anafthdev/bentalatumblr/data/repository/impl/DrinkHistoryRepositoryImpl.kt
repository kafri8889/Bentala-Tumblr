package com.anafthdev.bentalatumblr.data.repository.impl

import com.anafthdev.bentalatumblr.data.datasource.local.dao.DrinkHistoryDao
import com.anafthdev.bentalatumblr.data.model.db.DrinkHistory
import com.anafthdev.bentalatumblr.data.repository.DrinkHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkHistoryRepositoryImpl @Inject constructor(
	private val drinkHistoryDao: DrinkHistoryDao
): DrinkHistoryRepository {

	override fun getAll(): Flow<List<DrinkHistory>> {
		return drinkHistoryDao.getAll()
	}

	override fun getById(id: Int): Flow<DrinkHistory?> {
		return drinkHistoryDao.getById(id)
	}

	override fun getDaily(timeInMillis: Long): Flow<List<DrinkHistory>> {
		return drinkHistoryDao.getDaily(timeInMillis)
	}

	override fun getByDateRange(start: Long, end: Long): Flow<List<DrinkHistory>> {
		return drinkHistoryDao.getByDateRange(start, end)
	}

	override fun getByEarliestDate(): Flow<DrinkHistory?> {
		return drinkHistoryDao.getByEarliestDate()
	}

	override fun getByLatestDate(): Flow<DrinkHistory?> {
		return drinkHistoryDao.getByLatestDate()
	}

	override suspend fun insert(vararg t: DrinkHistory) {
		drinkHistoryDao.insert(*t)
	}

	override suspend fun insert(ts: Collection<DrinkHistory>) {
		drinkHistoryDao.insert(ts)
	}

	override suspend fun update(vararg t: DrinkHistory) {
		drinkHistoryDao.update(*t)
	}

	override suspend fun update(ts: Collection<DrinkHistory>) {
		drinkHistoryDao.update(ts)
	}

	override suspend fun delete(vararg t: DrinkHistory) {
		drinkHistoryDao.delete(*t)
	}

	override suspend fun delete(ts: Collection<DrinkHistory>) {
		drinkHistoryDao.delete(ts)
	}
}
