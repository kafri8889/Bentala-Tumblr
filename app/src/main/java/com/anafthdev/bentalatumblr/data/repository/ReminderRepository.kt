package com.anafthdev.bentalatumblr.data.repository

import com.anafthdev.bentalatumblr.data.model.db.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {

	fun getAll(): Flow<List<Reminder>>

	fun getById(id: Short): Flow<Reminder?>

	fun getActive(): Flow<List<Reminder>>

	fun getInactive(): Flow<List<Reminder>>

	suspend fun insert(vararg t: Reminder)

	suspend fun insert(ts: Collection<Reminder>)

	suspend fun update(vararg t: Reminder)

	suspend fun update(ts: Collection<Reminder>)

	suspend fun delete(vararg t: Reminder)

	suspend fun delete(ts: Collection<Reminder>)

}
