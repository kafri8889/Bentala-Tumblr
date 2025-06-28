package com.anafthdev.bentalatumblr.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.anafthdev.bentalatumblr.data.model.db.Reminder
import com.anafthdev.bentalatumblr.foundation.base.datasource.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ReminderDao: BaseDao<Reminder> {

	@Query("SELECT * FROM reminder")
	abstract fun getAll(): Flow<List<Reminder>>

	@Query("SELECT * FROM reminder WHERE id_reminder = :id")
	abstract fun getById(id: Short): Flow<Reminder?>

	@Query("SELECT * FROM reminder WHERE isActive_reminder = 1")
	abstract fun getActive(): Flow<List<Reminder>>

	@Query("SELECT * FROM reminder WHERE isActive_reminder = 0")
	abstract fun getInactive(): Flow<List<Reminder>>

}
