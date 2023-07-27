package com.batara.gigihproject.di

import com.batara.gigihproject.MainViewModel
import com.batara.gigihproject.SettingPreferences
import com.batara.gigihproject.core.domain.usecase.DisasterInteractor
import com.batara.gigihproject.core.domain.usecase.DisasterUseCase
import com.batara.gigihproject.map.MapViewModel
import com.batara.gigihproject.setting.SettingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<DisasterUseCase> { DisasterInteractor(get()) }
}

val viewModelModule = module {
    single { SettingPreferences.getInstance(androidContext()) }
    viewModel { MapViewModel(get()) }
    viewModel { (pref: SettingPreferences) -> MainViewModel(pref) }
    viewModel { (pref: SettingPreferences) -> SettingViewModel(pref) }
}