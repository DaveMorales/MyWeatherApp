package com.david.myweather.weatherList

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class WeatherListViewModelFactory @Inject constructor(
        private val weatherListViewModel: WeatherListViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherListViewModel::class.java!!)) {
            return weatherListViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}