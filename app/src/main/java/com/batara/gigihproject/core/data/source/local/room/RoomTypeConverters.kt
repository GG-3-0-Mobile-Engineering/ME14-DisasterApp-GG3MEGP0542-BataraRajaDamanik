package com.batara.gigihproject.core.data.source.local.room

import androidx.room.TypeConverter
import com.batara.gigihproject.core.data.source.remote.response.ListCoordinates
import com.batara.gigihproject.core.data.source.remote.response.Properties
import com.google.gson.Gson

class RoomTypeConverters {
    @TypeConverter
    fun convertCoordinateListToJSON(coordinates: ListCoordinates) : String = Gson().toJson(coordinates)
    @TypeConverter
    fun convertJSONToCoordinateList(json : String) : ListCoordinates = Gson().fromJson(json, ListCoordinates::class.java)
    @TypeConverter
    fun convertPropertiesToJSON(properties: Properties) : String = Gson().toJson(properties)
    @TypeConverter
    fun convertJSONToProperties(json : String) : Properties = Gson().fromJson(json, Properties::class.java)
}