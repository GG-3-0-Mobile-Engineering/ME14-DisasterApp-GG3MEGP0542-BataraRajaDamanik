package com.batara.gigihproject.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.batara.gigihproject.core.data.source.Resource
import com.batara.gigihproject.core.domain.model.Disaster
import com.batara.gigihproject.core.domain.usecase.DisasterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MapViewModel(private val disasterUseCase: DisasterUseCase) : ViewModel() {
    private val _disasterFilter = MutableLiveData<String>()
    val disasterFilter: LiveData<String> get() = _disasterFilter

    val disaster = disasterUseCase.getAllDisaster().asLiveData()

    fun updateDisasterFilter(filter: String) {
        _disasterFilter.value = filter
    }

    val filteredDisasters = disasterFilter.switchMap { filter ->
        disasterUseCase.getFilterDisaster(filter).asLiveData()
    }

    val filteredDisastersLocation = disasterFilter.switchMap { filter ->
        disasterUseCase.getFilterLocationDisaster(filter).asLiveData()
    }
}