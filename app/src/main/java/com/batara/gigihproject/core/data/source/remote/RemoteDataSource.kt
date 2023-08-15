package com.batara.gigihproject.core.data.source.remote

import android.util.Log
import com.batara.gigihproject.core.data.source.remote.network.ApiInterface
import com.batara.gigihproject.core.data.source.remote.network.ApiResponse
import com.batara.gigihproject.core.data.source.remote.response.GeometriesItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getAllDisaster() : Flow<ApiResponse<List<GeometriesItem>>>{
        return flow {
            try {
                val response = apiInterface.getDisaster(null,null, 604800)
                val dataArray = response.result.objects.output.geometries
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Error("Data Kosong"))
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFilterDisaster(filter : String) : Flow<ApiResponse<List<GeometriesItem>>>{
        return flow {
            try {
                val response = apiInterface.getDisaster(null,filter, 604800)
                val dataArray = response.result.objects.output.geometries
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Error("Data Kosong"))
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getAllDisaster: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFilterDisasterLocation(filter : String) : Flow<ApiResponse<List<GeometriesItem>>>{
        return flow {
            try {
                val response = apiInterface.getDisaster(filter,null, 604800)
                val dataArray = response.result.objects.output.geometries
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Error("Data Kosong"))
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getAllDisaster: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFilterDisasterLocation(startDate : String, endDate: String) : Flow<ApiResponse<List<GeometriesItem>>>{
        return flow {
            try {
                val response = apiInterface.getDateDisaster(startDate,endDate)
                val dataArray = response.result.objects.output.geometries
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Error("Data Kosong"))
                }
            }catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getAllDisaster: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    companion object{
        private const val TAG = "RemoteDataSource"
    }
}