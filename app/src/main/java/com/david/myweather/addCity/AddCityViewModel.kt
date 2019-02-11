package com.david.myweather.addCity

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.david.myweather.data.ResponseDto
import com.david.myweather.data.source.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject

class AddCityViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    val TAG: String = "AddCityViewModel"

    var citiesResult: MutableLiveData<ResponseDto> = MutableLiveData()
    var citiesError: MutableLiveData<String> = MutableLiveData()
    var citiesLoader: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var disposableObserver: DisposableObserver<ResponseDto>

    fun citiesResult(): LiveData<ResponseDto> {
        return citiesResult
    }

    fun citiesError(): LiveData<String> {
        return citiesError
    }

    fun citiesLoader(): LiveData<Boolean> {
        return citiesLoader
    }

    fun addCity(city: String) {

        disposableObserver = object : DisposableObserver<ResponseDto>() {
            override fun onComplete() {

            }

            override fun onNext(cities: ResponseDto) {
                citiesResult.postValue(cities)
                citiesLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError", e)
                citiesError.postValue(e.message)
                citiesLoader.postValue(false)
            }
        }

        weatherRepository.addCity(city).subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, MILLISECONDS)
            .subscribe(disposableObserver)
    }

    fun disposeElements() {
        try {
            if (!disposableObserver.isDisposed) disposableObserver.dispose()
        } catch (ex: Exception) {

        }
    }
}