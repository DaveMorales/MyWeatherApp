package com.david.myweather.data.source.remote

import com.david.myweather.data.GroupResponseDto
import com.david.myweather.data.ResponseDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    @GET("weather?units=metric")
    fun getWeatherByCity(
        @Query("q") city: String,
        @Query("APPID") appId: String
    ): Observable<ResponseDto>

    @GET("group?units=metric")
    fun getGroupWeather(
        @Query("id") ids: String,
        @Query("APPID") appId: String
    ): Observable<GroupResponseDto>
}