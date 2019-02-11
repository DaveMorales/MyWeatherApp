package com.david.myweather.data

import android.arch.persistence.room.ColumnInfo
import com.squareup.moshi.Json
import java.io.Serializable

class MainDto(

    @Json(name = "temp")
    @ColumnInfo(name = "temp")
    var temp: Double?= 0.0,

    @Json(name = "pressure")
    @ColumnInfo(name = "pressure")
    var pressure: Double?= 0.0,

    @Json(name = "humidity")
    @ColumnInfo(name = "humidity")
    var humidity: Double?= 0.0,

    @Json(name = "temp_min")
    @ColumnInfo(name = "tempMin")
    var tempMin: Double?= 0.0,

    @Json(name = "temp_max")
    @ColumnInfo(name = "tempMax")
    var tempMax: Double?= 0.0
) : Serializable