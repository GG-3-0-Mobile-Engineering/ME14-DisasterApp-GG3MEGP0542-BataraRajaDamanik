package com.batara.gigihproject.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.batara.gigihproject.core.data.source.local.entity.DisasterEntity
@TypeConverters(RoomTypeConverters::class)
@Database(entities = [DisasterEntity::class], version = 1, exportSchema = false)
abstract class DisasterDatabase : RoomDatabase() {

    abstract fun disasterDao() : DisasterDao

    companion object{
        @Volatile
        private var INSTANCE : DisasterDatabase? = null
    }
}