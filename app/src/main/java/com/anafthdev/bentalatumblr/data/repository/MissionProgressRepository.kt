package com.anafthdev.bentalatumblr.data.repository

import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import kotlinx.coroutines.flow.Flow

interface MissionProgressRepository {

	fun getAll(): Flow<List<MissionProgress>>

	fun getById(id: Int): Flow<MissionProgress?>

	suspend fun insert(vararg t: MissionProgress)

	suspend fun insert(ts: Collection<MissionProgress>)

	suspend fun update(vararg t: MissionProgress)

	suspend fun update(ts: Collection<MissionProgress>)

	suspend fun delete(vararg t: MissionProgress)

	suspend fun delete(ts: Collection<MissionProgress>)

}
