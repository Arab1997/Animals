package com.pratthamarora.animals.di

import com.pratthamarora.animals.model.AnimalService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: AnimalService)
}