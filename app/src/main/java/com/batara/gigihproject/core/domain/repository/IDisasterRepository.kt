package com.batara.gigihproject.core.domain.repository

import com.batara.gigihproject.core.data.source.Resource
import com.batara.gigihproject.core.domain.model.Disaster
import kotlinx.coroutines.flow.Flow

interface IDisasterRepository {
    fun getAllDisaster() : Flow<Resource<List<Disaster>>>
    fun getFilterDisaster(filter : String) : Flow<Resource<List<Disaster>>>
    fun getFilterLocationDisaster(filter : String) : Flow<Resource<List<Disaster>>>
    fun getFilterDateDisaster(startDate : String, endDate: String) : Flow<Resource<List<Disaster>>>
}