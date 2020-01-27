package com.pratthamarora.animals.di

import com.pratthamarora.animals.viewmodel.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ViewModelComponent {
    fun inject(viewModel: ListViewModel)
}