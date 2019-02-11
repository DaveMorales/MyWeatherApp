package com.david.myweather.addCity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class AddCityViewModelFactory @Inject constructor(
        private val addCityViewModel: AddCityViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCityViewModel::class.java!!)) {
            return addCityViewModel as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}