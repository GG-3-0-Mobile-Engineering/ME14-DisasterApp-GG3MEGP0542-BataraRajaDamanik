package com.batara.gigihproject.core.di

import com.batara.gigihproject.core.data.source.DisasterRepository
import com.batara.gigihproject.core.domain.repository.IDisasterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepositoryDisaster(disasterRepository: DisasterRepository) : IDisasterRepository
}