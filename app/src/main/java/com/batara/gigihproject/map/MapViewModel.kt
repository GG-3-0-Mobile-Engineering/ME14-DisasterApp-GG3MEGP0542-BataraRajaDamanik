package com.batara.gigihproject.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.batara.gigihproject.core.domain.usecase.DisasterUseCase

class MapViewModel(disasterUseCase: DisasterUseCase) : ViewModel() {
    val disaster = disasterUseCase.getAllDisaster().asLiveData()
}