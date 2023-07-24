package com.batara.gigihproject.di

import com.batara.gigihproject.core.domain.usecase.DisasterInteractor
import com.batara.gigihproject.core.domain.usecase.DisasterUseCase
import com.batara.gigihproject.map.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<DisasterUseCase> { DisasterInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MapViewModel(get()) }
}