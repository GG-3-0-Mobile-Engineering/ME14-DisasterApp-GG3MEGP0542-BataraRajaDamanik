package com.batara.gigihproject.core.data.source.local

import com.batara.gigihproject.core.data.source.local.entity.DisasterEntity
import com.batara.gigihproject.core.data.source.local.room.DisasterDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val disasterDao: DisasterDao) {
    fun getAllDisaster() : Flow<List<DisasterEntity>> = disasterDao.getAllDisaster()

    suspend fun insertDisaster(listDisaster : List<DisasterEntity>) = disasterDao.insertDisaster(listDisaster)

    suspend fun deleteDisaster() = disasterDao.deleteAll()
}