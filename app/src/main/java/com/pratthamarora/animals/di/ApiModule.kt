package com.pratthamarora.animals.di

import com.pratthamarora.animals.model.AnimalService
import com.pratthamarora.animals.model.AnimalsApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @Provides
    fun provideAnimalApi(): AnimalsApi {
        return Retrofit.Builder()
            .baseUrl("https://us-central1-apis-4674e.cloudfunctions.net")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(AnimalsApi::class.java)
    }

    @Provides
    fun provideAnimalApiService(): AnimalService {
        return AnimalService()
    }
}