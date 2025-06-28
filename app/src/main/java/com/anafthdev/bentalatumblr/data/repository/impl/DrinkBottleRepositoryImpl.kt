package com.anafthdev.bentalatumblr.data.repository.impl

import com.anafthdev.bentalatumblr.data.datasource.local.dao.DrinkBottleDao
import com.anafthdev.bentalatumblr.data.model.db.DrinkBottle
import com.anafthdev.bentalatumblr.data.repository.DrinkBottleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DrinkBottleRepositoryImpl @Inject constructor(
    private val drinkBottleDao: DrinkBottleDao
) : DrinkBottleRepository {

    override fun getAll(): Flow<List<DrinkBottle>> {
        return drinkBottleDao.getAll()
    }

    override fun getById(id: Int): Flow<DrinkBottle?> {
        return drinkBottleDao.getById(id)
    }

    override suspend fun insert(vararg t: DrinkBottle) {
        drinkBottleDao.insert(*t)
    }

    override suspend fun insert(ts: Collection<DrinkBottle>) {
        drinkBottleDao.insert(ts)
    }

    override suspend fun update(vararg t: DrinkBottle) {
        drinkBottleDao.update(*t)
    }

    override suspend fun update(ts: Collection<DrinkBottle>) {
        drinkBottleDao.update(ts)
    }

    override suspend fun delete(vararg t: DrinkBottle) {
        drinkBottleDao.delete(*t)
    }

    override suspend fun delete(ts: Collection<DrinkBottle>) {
        drinkBottleDao.delete(ts)
    }
}
