package com.pratthamarora.animals.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.pratthamarora.animals.di.DaggerViewModelComponent
import com.pratthamarora.animals.model.AnimalService
import com.pratthamarora.animals.model.Animals
import com.pratthamarora.animals.model.ApiKey
import com.pratthamarora.animals.utils.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application) : AndroidViewModel(application) {

    val animals by lazy {
        MutableLiveData<List<Animals>>()
    }
    val loading by lazy {
        MutableLiveData<Boolean>()
    }
    val loadingError by lazy {
        MutableLiveData<Boolean>()
    }
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var apiService: AnimalService

    init {
        DaggerViewModelComponent.create().inject(this)
    }

    private val prefs = SharedPreferencesHelper(getApplication())

    private var invalidApiKey = false

    fun refresh() {

        loading.value = true
        invalidApiKey = false
        val key = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }


    }

    fun hardRefresh() {
        loading.value = true
        getKey()
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(
                    object : DisposableSingleObserver<ApiKey>() {
                        override fun onSuccess(key: ApiKey) {
                            if (key.key.isNullOrEmpty()) {
                                loadingError.value = true

                                loading.value = false
                            } else {
                                prefs.saveApiKey(key.key)
                                getAnimals(key.key)
                            }
                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            loading.value = false
                            loadingError.value = true
                        }

                    })

        )


    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Animals>>() {
                    override fun onSuccess(t: List<Animals>) {
                        loadingError.value = false
                        animals.value = t
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            loading.value = false
                            animals.value = null
                            loadingError.value = true
                        }

                    }

                })
        )


    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}