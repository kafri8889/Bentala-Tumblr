package com.anafthdev.bentalatumblr.data.repository.impl

import com.anafthdev.bentalatumblr.data.datasource.local.dao.MissionProgressDao
import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import com.anafthdev.bentalatumblr.data.repository.MissionProgressRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MissionProgressRepositoryImpl @Inject constructor(
    private val missionProgressDao: MissionProgressDao
) : MissionProgressRepository {

    override fun getAll(): Flow<List<MissionProgress>> {
        return missionProgressDao.getAll()
    }

    override fun getById(id: Int): Flow<MissionProgress?> {
        return missionProgressDao.getById(id)
    }

    override suspend fun insert(vararg t: MissionProgress) {
        missionProgressDao.insert(*t)
    }

    override suspend fun insert(ts: Collection<MissionProgress>) {
        missionProgressDao.insert(ts)
    }

    override suspend fun update(vararg t: MissionProgress) {
        missionProgressDao.update(*t)
    }

    override suspend fun update(ts: Collection<MissionProgress>) {
        missionProgressDao.update(ts)
    }

    override suspend fun delete(vararg t: MissionProgress) {
        missionProgressDao.delete(*t)
    }

    override suspend fun delete(ts: Collection<MissionProgress>) {
        missionProgressDao.delete(ts)
    }
}
