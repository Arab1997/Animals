package com.pratthamarora.animals.di

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.pratthamarora.animals.utils.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class PrefsModule {

    @Provides
    @Singleton    //single instance for one resource
    @TypeOfContext(CONTEXT_APP)
    fun provideSharedPrefs(app: Application): SharedPreferencesHelper {
        return SharedPreferencesHelper(app)
    }

    @Provides
    @Singleton
    @TypeOfContext(CONTEXT_ACT)
    fun provideActivitySharedPref(activity: AppCompatActivity): SharedPreferencesHelper {
        return SharedPreferencesHelper(activity)
    }
}

const val CONTEXT_APP = "Application Context"
const val CONTEXT_ACT = "Activity Context"

@Qualifier
annotation class TypeOfContext(val type: String)