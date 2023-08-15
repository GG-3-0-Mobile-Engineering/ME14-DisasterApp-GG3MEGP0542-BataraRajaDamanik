package com.batara.gigihproject.core.di

import android.content.Context
import com.batara.gigihproject.SettingPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingPreferencesModule {

    @Provides
    @Singleton
    fun provideSettingPreferences(@ApplicationContext context: Context) : SettingPreferences{
        return SettingPreferences.getInstance(context)
    }
}