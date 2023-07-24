package com.batara.gigihproject.core.domain.usecase

import com.batara.gigihproject.core.data.source.Resource
import com.batara.gigihproject.core.domain.model.Disaster
import com.batara.gigihproject.core.domain.repository.IDisasterRepository
import kotlinx.coroutines.flow.Flow

class DisasterInteractor(private val disasterIDisasterRepository: IDisasterRepository) : DisasterUseCase {
    override fun getAllDisaster() = disasterIDisasterRepository.getAllDisaster()
}