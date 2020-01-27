package com.pratthamarora.animals.model

import com.pratthamarora.animals.di.DaggerApiComponent
import io.reactivex.Single
import javax.inject.Inject

class AnimalService {

    @Inject
    lateinit var api: AnimalsApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getApiKey(): Single<ApiKey> {
        return api.getApiKey()
    }

    fun getAnimals(key: String): Single<List<Animals>> {
        return api.getAnimals(key)
    }
}