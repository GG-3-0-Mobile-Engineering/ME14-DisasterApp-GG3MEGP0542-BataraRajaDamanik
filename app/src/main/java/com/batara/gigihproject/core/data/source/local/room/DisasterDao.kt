package com.batara.gigihproject.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.batara.gigihproject.core.data.source.local.entity.DisasterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DisasterDao {

    @Query("SELECT * FROM disaster")
    fun getAllDisaster() : Flow<List<DisasterEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDisaster(disaster : List<DisasterEntity>)

    @Query("DELETE FROM disaster")
    suspend fun deleteAll()
}