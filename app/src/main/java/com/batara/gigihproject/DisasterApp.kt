package com.batara.gigihproject

import android.app.Application
import com.batara.gigihproject.core.di.databaseModule
import com.batara.gigihproject.core.di.networkModule
import com.batara.gigihproject.core.di.repositoryModule
import com.batara.gigihproject.di.useCaseModule
import com.batara.gigihproject.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level


class DisasterApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@DisasterApp)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}