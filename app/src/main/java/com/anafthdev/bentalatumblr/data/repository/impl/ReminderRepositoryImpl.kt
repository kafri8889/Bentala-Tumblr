package com.anafthdev.bentalatumblr.data.repository.impl

import com.anafthdev.bentalatumblr.data.datasource.local.dao.ReminderDao
import com.anafthdev.bentalatumblr.data.model.db.Reminder
import com.anafthdev.bentalatumblr.data.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
	private val reminderDao: ReminderDao
): ReminderRepository {

	override fun getAll(): Flow<List<Reminder>> {
		return reminderDao.getAll()
	}

	override fun getById(id: Short): Flow<Reminder?> {
		return reminderDao.getById(id)
	}

	override fun getActive(): Flow<List<Reminder>> {
		return reminderDao.getActive()
	}

	override fun getInactive(): Flow<List<Reminder>> {
		return reminderDao.getInactive()
	}

	override suspend fun insert(vararg t: Reminder) {
		reminderDao.insert(*t)
	}

	override suspend fun insert(ts: Collection<Reminder>) {
		reminderDao.insert(ts)
	}

	override suspend fun update(vararg t: Reminder) {
		reminderDao.update(*t)
	}

	override suspend fun update(ts: Collection<Reminder>) {
		reminderDao.update(ts)
	}

	override suspend fun delete(vararg t: Reminder) {
		reminderDao.delete(*t)
	}

	override suspend fun delete(ts: Collection<Reminder>) {
		reminderDao.delete(ts)
	}
}
