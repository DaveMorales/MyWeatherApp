package com.david.myweather.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.david.myweather.data.ResponseDto
import com.david.myweather.data.WeatherDtoTypeConverter

@Database(entities = arrayOf(ResponseDto::class), version = 1)
@TypeConverters(WeatherDtoTypeConverter::class)
abstract class Database : RoomDatabase() {
  abstract fun weatherDao(): WeatherDao
}