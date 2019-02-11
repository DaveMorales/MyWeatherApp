package com.david.myweather.weatherList

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.david.myweather.data.ResponseDto
import com.david.myweather.data.source.WeatherRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit.MILLISECONDS
import javax.inject.Inject


class WeatherListViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {

    val TAG: String = "WeatherListViewModel"
    var weatherResult: MutableLiveData<List<ResponseDto>> = MutableLiveData()
    var weatherError: MutableLiveData<String> = MutableLiveData()
    var weatherLoader: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var savedCitiesObserver: DisposableObserver<List<ResponseDto>>
    lateinit var disposableObserver: DisposableObserver<List<ResponseDto>>

    fun weatherResult(): LiveData<List<ResponseDto>> {
        return weatherResult
    }

    fun weatherError(): LiveData<String> {
        return weatherError
    }

    fun weatherLoader(): LiveData<Boolean> {
        return weatherLoader
    }

    fun loadWeatherInfo() {

        disposableObserver = object : DisposableObserver<List<ResponseDto>>() {
            override fun onComplete() {
            }

            override fun onNext(cities: List<ResponseDto>) {
                val cityIds: String = ""
                for (i in cities.indices - 1) {
                    cityIds.plus(cities[i].id).plus(",")
                }
                cityIds.plus(cities.last().id)


                weatherResult.postValue(cities)
                weatherLoader.postValue(false)
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError", e)
                weatherError.postValue(e.message)
                weatherLoader.postValue(false)
            }
        }
        savedCitiesObserver = object : DisposableObserver<List<ResponseDto>>() {
            var cityIds: String = ""
            override fun onComplete() {
                if (cityIds.isNotEmpty())
                    weatherRepository.getUpdatedWeather(cityIds)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .debounce(400, MILLISECONDS)
                        .subscribe(disposableObserver)
                else
                    weatherLoader.postValue(false)
            }

            override fun onNext(cities: List<ResponseDto>) {
                for (i in cities.indices - 1) {
                    cityIds = cityIds.plus(cities[i].id).plus(",")
                }
                if (cities.isNotEmpty())
                    cityIds = cityIds.plus(cities.last().id)

                Log.e(TAG, "ids -> $cityIds")
            }

            override fun onError(e: Throwable) {
                Log.e(TAG, "onError", e)
            }
        }

        weatherRepository.getSavedCities()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .debounce(400, MILLISECONDS)
            .subscribe(savedCitiesObserver)
    }

    fun disposeElements() {
        if (!savedCitiesObserver.isDisposed) savedCitiesObserver.dispose()
    }

}