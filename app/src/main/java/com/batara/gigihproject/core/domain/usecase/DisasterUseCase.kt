package com.batara.gigihproject.core.domain.usecase

import com.batara.gigihproject.core.data.source.Resource
import com.batara.gigihproject.core.domain.model.Disaster
import kotlinx.coroutines.flow.Flow

interface DisasterUseCase {
    fun getAllDisaster(): Flow<Resource<List<Disaster>>>
    fun getFilterDisaster(filter : String): Flow<Resource<List<Disaster>>>

    fun getFilterLocationDisaster(filter : String): Flow<Resource<List<Disaster>>>
}