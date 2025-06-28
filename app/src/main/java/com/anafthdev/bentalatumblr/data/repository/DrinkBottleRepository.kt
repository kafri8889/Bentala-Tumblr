package com.anafthdev.bentalatumblr.data.repository

import com.anafthdev.bentalatumblr.data.model.db.DrinkBottle
import kotlinx.coroutines.flow.Flow

interface DrinkBottleRepository {

	fun getAll(): Flow<List<DrinkBottle>>

	fun getById(id: Int): Flow<DrinkBottle?>

	suspend fun insert(vararg t: DrinkBottle)

	suspend fun insert(ts: Collection<DrinkBottle>)

	suspend fun update(vararg t: DrinkBottle)

	suspend fun update(ts: Collection<DrinkBottle>)

	suspend fun delete(vararg t: DrinkBottle)

	suspend fun delete(ts: Collection<DrinkBottle>)

}
