package com.batara.gigihproject.core.di

import androidx.room.Room
import com.batara.gigihproject.core.data.source.DisasterRepository
import com.batara.gigihproject.core.data.source.local.LocalDataSource
import com.batara.gigihproject.core.data.source.local.room.DisasterDatabase
import com.batara.gigihproject.core.data.source.remote.RemoteDataSource
import com.batara.gigihproject.core.data.source.remote.network.ApiInterface
import com.batara.gigihproject.core.domain.repository.IDisasterRepository
import com.batara.gigihproject.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<DisasterDatabase>().disasterDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            DisasterDatabase::class.java, "Disaster.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://data.petabencana.id/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiInterface::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single <IDisasterRepository> { DisasterRepository(get(), get(), get()) }
}