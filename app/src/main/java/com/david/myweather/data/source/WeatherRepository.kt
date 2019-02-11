package com.david.myweather.data.source

import android.util.Log
import androidx.work.ListenableWorker
import com.david.myweather.data.GroupResponseDto
import com.david.myweather.data.ResponseDto
import com.david.myweather.data.source.local.WeatherDao
import com.david.myweather.data.source.remote.ApiInterface
import com.david.myweather.utils.Constants
import com.david.myweather.utils.Utils
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    val apiInterface: ApiInterface,
    val weatherDao: WeatherDao, val utils: Utils
) {
    val TAG: String = "Reposi"

    fun addCity(city: String): Observable<ResponseDto> {
        Log.e(TAG, "addCity()")
        val hasConnection = utils.isConnectedToInternet()
        var observableFromRemote: Observable<ResponseDto>? = null

        if (hasConnection) {
            observableFromRemote = addCityService(city, Constants.APP_ID)
        }

        return observableFromRemote!!
    }

    fun addCityService(city: String, appId: String): Observable<ResponseDto> {
        Log.e(TAG, "addCityService()")
        return apiInterface.getWeatherByCity(city, appId)
            .doOnNext {
                Log.e("WeatherFromRemote -> ", it.toString())
                weatherDao.insertWeatherData(it)
            }
    }

    fun getSavedCities(): Observable<List<ResponseDto>> {
        Log.e(TAG, "getSavedCities()")

        val observableFromLocal = getWeatherInfoFromLocal()

        return observableFromLocal
    }

    fun getUpdatedWeather(ids: String): Observable<List<ResponseDto>> {
        Log.e(TAG, "getUpdatedWeather()")
        val hasConnection = utils.isConnectedToInternet()
        var observableFromRemote: Observable<List<ResponseDto>>? = null
        if (hasConnection) {
            observableFromRemote=getWeatherInfoFromRemote(ids, Constants.APP_ID)
        }

        val observableFromDb = getWeatherInfoFromLocal()

        return if (hasConnection) Observable.concatArrayEager(observableFromRemote, observableFromDb)
        else observableFromDb

    }


    fun getWeatherInfoFromRemote(ids: String, appId: String): Observable<List<ResponseDto>> {
        Log.e(TAG, "getWeatherInfoFromRemote()")

        return apiInterface.getGroupWeather(ids, appId)
            .doOnNext {
                //Print log it.size :)
                Log.e("REPOSITORY API * ", it.weather.toString())
                for (item in it.weather) {
                    weatherDao.updateWeather(item!!.weather, item.id!!)
                }
            }
            .map {t: GroupResponseDto -> listOf<ResponseDto>()}
    }

    fun getWeatherInfoFromLocal(): Observable<List<ResponseDto>> {
        Log.e(TAG, "getWeatherInfoFromLocal()")
        return weatherDao.getWeatherData()
            .toObservable()
            .doOnNext {
                //Print log it.size :)
                Log.e("REPOSITORY DB size ", it.size.toString())
                Log.e("REPOSITORY DB *** ", it.toString())
            }
    }
}