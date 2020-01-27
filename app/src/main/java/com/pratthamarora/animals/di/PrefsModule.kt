package com.pratthamarora.animals.di

import android.app.Application
import com.pratthamarora.animals.utils.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton    //single instance for one resource
    fun provideSharedPrefs(app: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(app)
    }
}