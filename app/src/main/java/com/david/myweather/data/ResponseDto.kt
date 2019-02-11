package com.david.myweather.data

import android.arch.persistence.room.*
import com.squareup.moshi.Json
import java.io.Serializable

@Entity(
    tableName = "weatherData"
)
data class ResponseDto(

    @Json(name = "id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = 0,

    @Json(name = "name")
    @ColumnInfo(name = "name")
    var name: String? = "",

    @Json(name = "main")
    @Embedded
    var main: MainDto,

    @Json(name = "weather")
    @TypeConverters(WeatherDtoTypeConverter::class)
    var weather: List<WeatherDto?> = listOf()

) : Serializable