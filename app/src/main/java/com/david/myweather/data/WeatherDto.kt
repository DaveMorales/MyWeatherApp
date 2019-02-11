package com.david.myweather.data

import android.arch.persistence.room.ColumnInfo
import com.squareup.moshi.Json
import java.io.Serializable

class WeatherDto(

    @Json(name = "main")
    @ColumnInfo(name = "main")
    var main: String?= "",

    @Json(name = "description")
    @ColumnInfo(name = "description")
    var description: String?= "",

    @Json(name = "icon")
    @ColumnInfo(name = "icon")
    var icon: String?= ""

) : Serializable