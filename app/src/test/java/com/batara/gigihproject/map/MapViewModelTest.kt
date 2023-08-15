package com.batara.gigihproject.map

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.batara.gigihproject.core.data.source.Resource
import com.batara.gigihproject.core.domain.model.Disaster
import com.batara.gigihproject.core.domain.usecase.DisasterUseCase
import com.batara.gigihproject.util.DataDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MapViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mapViewModel: MapViewModel

    @Mock
    private lateinit var mockDisasterUseCase: DisasterUseCase
    private val lifecycleOwner = LifecycleRegistry(mock(LifecycleOwner::class.java))
    private val dummyDisaster = DataDummy.generateDummyDisaster()

    @Mock
    private lateinit var disasterFilterObserver: Observer<Resource<List<Disaster>>>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        val mockFlow : Flow<Resource<List<Disaster>>> = flowOf(Resource.Success(dummyDisaster))
        `when`(mockDisasterUseCase.getAllDisaster()).thenReturn(mockFlow)
        mapViewModel = MapViewModel(mockDisasterUseCase)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
    }


    @Test
    fun `updateDisasterFilter updates the filter correctly`() {
        try {
            val filterValue = "Flood"
            val expectedDisaster = flowOf(Resource.Success(dummyDisaster))

            Dispatchers.setMain(TestCoroutineDispatcher())

            `when`(mockDisasterUseCase.getFilterDisaster(filterValue))
                .thenReturn(expectedDisaster)

            mapViewModel.updateDisasterFilter(filterValue)

            val observer = Observer<Resource<List<Disaster>>> { }
            mapViewModel.filteredDisasters.observeForever(observer)

            Mockito.verify(mockDisasterUseCase, Mockito.times(1)).getFilterDisaster(filterValue)
            Assert.assertNotNull(mapViewModel.filteredDisasters.value)
        }finally {
            Dispatchers.resetMain()
            mapViewModel.filteredDisasters.removeObserver(disasterFilterObserver)
        }
    }

    @Test
    fun `updateDisasterFilterLocation updates the filter correctly`() {
        try {
            val filterValue = "ID-JK"
            val expectedDisaster = flowOf(Resource.Success(dummyDisaster))

            Dispatchers.setMain(TestCoroutineDispatcher())

            `when`(mockDisasterUseCase.getFilterLocationDisaster(filterValue))
                .thenReturn(expectedDisaster)

            mapViewModel.updateDisasterFilterLocation(filterValue)

            val observer = Observer<Resource<List<Disaster>>> { }
            mapViewModel.filteredDisastersLocation.observeForever(observer)

            Mockito.verify(mockDisasterUseCase, Mockito.times(1)).getFilterLocationDisaster(filterValue)
            Assert.assertNotNull(mapViewModel.filteredDisastersLocation.value)
        }finally {
            Dispatchers.resetMain()
            mapViewModel.filteredDisastersLocation.removeObserver(disasterFilterObserver)
        }
    }

    @Test
    fun `updateDisasterFilterDate updates the filter correctly`() {
        try {
            val filterValue = listOf<String>("2020-20-02", "2020-25-02")
            val expectedDisaster = flowOf(Resource.Success(dummyDisaster))

            Dispatchers.setMain(TestCoroutineDispatcher())

            `when`(mockDisasterUseCase.getFilterDateDisaster(filterValue[0], filterValue[1]))
                .thenReturn(expectedDisaster)

            mapViewModel.updateDisasterFilterDate(filterValue)

            val observer = Observer<Resource<List<Disaster>>> { }
            mapViewModel.filteredDisastersDate.observeForever(observer)

            Mockito.verify(mockDisasterUseCase, Mockito.times(1)).getFilterDateDisaster(filterValue[0], filterValue[1])
            Assert.assertNotNull(mapViewModel.filteredDisastersDate.value)
        }finally {
            Dispatchers.resetMain()
            mapViewModel.filteredDisastersDate.removeObserver(disasterFilterObserver)
        }
    }

    @Test
    fun `updateDisasterFilter updates the filter incorrectly`() {
        try {
            val filterValue = 123.toString()
            val expectedDisaster = flowOf(Resource.Error("incorrect type", dummyDisaster))

            Dispatchers.setMain(TestCoroutineDispatcher())

            `when`(mockDisasterUseCase.getFilterDisaster(filterValue))
                .thenReturn(expectedDisaster)

            mapViewModel.updateDisasterFilter(filterValue)

            val observer = Observer<Resource<List<Disaster>>> { }
            mapViewModel.filteredDisasters.observeForever(observer)

            Mockito.verify(mockDisasterUseCase, Mockito.times(1)).getFilterDisaster(filterValue)
            Assert.assertNotNull(mapViewModel.filteredDisasters.value)
        }finally {
            Dispatchers.resetMain()
            mapViewModel.filteredDisasters.removeObserver(disasterFilterObserver)
        }
    }
}