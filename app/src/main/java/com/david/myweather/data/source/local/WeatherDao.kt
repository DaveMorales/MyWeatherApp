package com.david.myweather.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.david.myweather.data.ResponseDto
import com.david.myweather.data.WeatherDto
import io.reactivex.Single

@Dao
interface WeatherDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertWeatherData(weatherData: ResponseDto)

  @Query("SELECT * FROM weatherData")
  fun getWeatherData(): Single<List<ResponseDto>>

  @Query("UPDATE weatherData SET weather = :newWeather WHERE id=:id")
  fun updateWeather(newWeather: List<WeatherDto?>, id:Int)

  @Insert(
      onConflict = OnConflictStrategy.REPLACE
  )
  fun insertAllWeatherData(weatherData: List<ResponseDto>)
}