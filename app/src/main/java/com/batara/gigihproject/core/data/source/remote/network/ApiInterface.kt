package com.batara.gigihproject.core.data.source.remote.network

import com.batara.gigihproject.core.data.source.remote.response.DisasterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("reports")
    suspend fun getDisaster(@Query("admin") admin : String?, @Query("disaster") disaster : String?, @Query("timeperiod") timeperiod : Int?) : DisasterResponse
}