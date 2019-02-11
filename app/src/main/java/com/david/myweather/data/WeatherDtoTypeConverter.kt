package com.david.myweather.data

import android.arch.persistence.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class WeatherDtoTypeConverter{

    private val moshi = Moshi.Builder().build()
    private val listMyData = Types.newParameterizedType(List::class.java, WeatherDto::class.java)
    private val adapter = moshi.adapter<List<WeatherDto>>(listMyData)

    @TypeConverter
    fun stringToList(json: String?): List<WeatherDto>? {
        if (json == null) {
            return emptyList()
        }
        try {
            return adapter.fromJson(json)
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }

    @TypeConverter
    fun ListToString(list: List<WeatherDto>): String {
        return adapter.toJson(list)
    }
}