package com.pratthamarora.animals.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AnimalService {

    private val api = Retrofit.Builder()
        .baseUrl("https://us-central1-apis-4674e.cloudfunctions.net")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(AnimalsApi::class.java)

    fun getApiKey(): Single<ApiKey> {
        return api.getApiKey()
    }

    fun getAnimals(key: String): Single<List<Animals>> {
        return api.getAnimals(key)
    }
}