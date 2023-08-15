package com.batara.gigihproject.di

import com.batara.gigihproject.core.domain.usecase.DisasterInteractor
import com.batara.gigihproject.core.domain.usecase.DisasterUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideDisasterUseCase(disasterInteractor: DisasterInteractor) : DisasterUseCase
}