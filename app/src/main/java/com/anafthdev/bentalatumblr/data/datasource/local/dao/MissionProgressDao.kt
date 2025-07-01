package com.anafthdev.bentalatumblr.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.anafthdev.bentalatumblr.data.model.db.MissionProgress
import com.anafthdev.bentalatumblr.foundation.base.datasource.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MissionProgressDao : BaseDao<MissionProgress> {

    @Query("select * from mission_progress")
    abstract fun getAll(): Flow<List<MissionProgress>>

    @Query("select * from mission_progress where id like :id")
    abstract fun getById(id: Int): Flow<MissionProgress?>

}
