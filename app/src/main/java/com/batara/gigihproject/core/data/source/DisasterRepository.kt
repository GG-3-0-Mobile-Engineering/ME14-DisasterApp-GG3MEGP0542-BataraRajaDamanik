package com.batara.gigihproject.core.data.source

import com.batara.gigihproject.core.data.source.local.LocalDataSource
import com.batara.gigihproject.core.data.source.remote.RemoteDataSource
import com.batara.gigihproject.core.data.source.remote.network.ApiResponse
import com.batara.gigihproject.core.data.source.remote.response.GeometriesItem
import com.batara.gigihproject.core.domain.model.Disaster
import com.batara.gigihproject.core.domain.repository.IDisasterRepository
import com.batara.gigihproject.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DisasterRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IDisasterRepository{

    override fun getAllDisaster(): Flow<Resource<List<Disaster>>> =
        object : NetworkBoundResource<List<Disaster>, List<GeometriesItem>>(){
            override fun loadFromDB(): Flow<List<Disaster>> {
                return localDataSource.getAllDisaster().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Disaster>?): Boolean =
//                data == null || data.isEmpty()
                true

            override suspend fun createCall(): Flow<ApiResponse<List<GeometriesItem>>> =
                remoteDataSource.getAllDisaster()

            override suspend fun saveCallResult(data: List<GeometriesItem>) {
                val disasterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.deleteDisaster()
                localDataSource.insertDisaster(disasterList)
            }
        }.asFlow()

    override fun getFilterDisaster(filter : String): Flow<Resource<List<Disaster>>> =
        object : NetworkBoundResource<List<Disaster>, List<GeometriesItem>>(){
            override fun loadFromDB(): Flow<List<Disaster>> {
                return localDataSource.getAllDisaster().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Disaster>?): Boolean =
//                data == null || data.isEmpty()
                true

            override suspend fun createCall(): Flow<ApiResponse<List<GeometriesItem>>> =
                remoteDataSource.getFilterDisaster(filter)

            override suspend fun saveCallResult(data: List<GeometriesItem>) {
                val disasterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.deleteDisaster()
                localDataSource.insertDisaster(disasterList)
            }
        }.asFlow()

    override fun getFilterLocationDisaster(filter : String): Flow<Resource<List<Disaster>>> =
        object : NetworkBoundResource<List<Disaster>, List<GeometriesItem>>(){
            override fun loadFromDB(): Flow<List<Disaster>> {
                return localDataSource.getAllDisaster().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Disaster>?): Boolean =
//                data == null || data.isEmpty()
                true

            override suspend fun createCall(): Flow<ApiResponse<List<GeometriesItem>>> =
                remoteDataSource.getFilterDisasterLocation(filter)

            override suspend fun saveCallResult(data: List<GeometriesItem>) {
                val disasterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.deleteDisaster()
                localDataSource.insertDisaster(disasterList)
            }
        }.asFlow()

    override fun getFilterDateDisaster(
        startDate: String,
        endDate: String
    ): Flow<Resource<List<Disaster>>> =
        object : NetworkBoundResource<List<Disaster>, List<GeometriesItem>>(){
            override fun loadFromDB(): Flow<List<Disaster>> {
                return localDataSource.getAllDisaster().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Disaster>?): Boolean =
//                data == null || data.isEmpty()
                true

            override suspend fun createCall(): Flow<ApiResponse<List<GeometriesItem>>> =
                remoteDataSource.getFilterDisasterLocation(startDate, endDate)

            override suspend fun saveCallResult(data: List<GeometriesItem>) {
                val disasterList = DataMapper.mapResponsesToEntities(data)
                localDataSource.deleteDisaster()
                localDataSource.insertDisaster(disasterList)
            }
        }.asFlow()

    companion object{
        @Volatile
        private var instance : DisasterRepository? = null
    }
}