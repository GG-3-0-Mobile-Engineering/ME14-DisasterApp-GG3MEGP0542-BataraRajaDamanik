package com.batara.gigihproject.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.batara.gigihproject.core.domain.usecase.DisasterUseCase

class MapViewModel(disasterUseCase: DisasterUseCase) : ViewModel() {
    private val _disasterFilter = MutableLiveData<String>()
    private val _disasterFilterLocation = MutableLiveData<String>()
    private val _disasterFilterDate = MutableLiveData<List<String>>()
    val disasterFilter: LiveData<String> get() = _disasterFilter
    val disasterFilterLocation: LiveData<String> get() = _disasterFilterLocation
    val disasterFilterDate: LiveData<List<String>> get() = _disasterFilterDate

    val disaster = disasterUseCase.getAllDisaster().asLiveData()

    fun updateDisasterFilter(filter: String) {
        _disasterFilter.value = filter
    }

    fun updateDisasterFilterLocation(filter: String) {
        _disasterFilterLocation.value = filter
    }

    fun updateDisasterFilterDate(date: List<String>) {
        _disasterFilterDate.value = date
    }

    val filteredDisasters = disasterFilter.switchMap { filter ->
        disasterUseCase.getFilterDisaster(filter).asLiveData()
    }

    val filteredDisastersLocation = disasterFilterLocation.switchMap { filter ->
        disasterUseCase.getFilterLocationDisaster(filter).asLiveData()
    }

    val filteredDisastersDate = disasterFilterDate.switchMap { date ->
        disasterUseCase.getFilterDateDisaster(date[0], date[1]).asLiveData()
    }
}