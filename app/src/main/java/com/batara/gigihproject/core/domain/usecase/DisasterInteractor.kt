package com.batara.gigihproject.core.domain.usecase

import com.batara.gigihproject.core.data.source.Resource
import com.batara.gigihproject.core.domain.model.Disaster
import com.batara.gigihproject.core.domain.repository.IDisasterRepository
import kotlinx.coroutines.flow.Flow

class DisasterInteractor(private val disasterIDisasterRepository: IDisasterRepository) : DisasterUseCase {
    override fun getAllDisaster() = disasterIDisasterRepository.getAllDisaster()
    override fun getFilterDisaster(filter: String): Flow<Resource<List<Disaster>>> = disasterIDisasterRepository.getFilterDisaster(filter)
    override fun getFilterLocationDisaster(filter: String): Flow<Resource<List<Disaster>>> = disasterIDisasterRepository.getFilterLocationDisaster(filter)
    override fun getFilterDateDisaster(startDate: String, endDate: String): Flow<Resource<List<Disaster>>> =
        disasterIDisasterRepository.getFilterDateDisaster(startDate, endDate)
}