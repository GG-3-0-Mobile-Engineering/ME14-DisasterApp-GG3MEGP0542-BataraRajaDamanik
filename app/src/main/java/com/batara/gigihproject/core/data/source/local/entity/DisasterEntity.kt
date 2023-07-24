package com.batara.gigihproject.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.batara.gigihproject.core.data.source.remote.response.ListCoordinates
import com.batara.gigihproject.core.data.source.remote.response.Properties

@Entity(tableName = "disaster")
data class DisasterEntity(
    @PrimaryKey
    @ColumnInfo(name = "pkey")
    val pkey : Int,

    @ColumnInfo(name = "coordinates")
    val coordinates: ListCoordinates,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "properties")
    val properties: Properties
)
