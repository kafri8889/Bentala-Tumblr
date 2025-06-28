package com.anafthdev.bentalatumblr.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.anafthdev.bentalatumblr.data.model.db.DrinkBottle
import com.anafthdev.bentalatumblr.foundation.base.datasource.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DrinkBottleDao : BaseDao<DrinkBottle> {

    @Query("select * from drink_bottle")
    abstract fun getAll(): Flow<List<DrinkBottle>>

    @Query("select * from drink_bottle where id like :id")
    abstract fun getById(id: Int): Flow<DrinkBottle?>

}
