package com.david.myweather.data

import com.squareup.moshi.Json
import java.io.Serializable

data class GroupResponseDto(

    @Json(name = "list")
    var weather: List<ResponseDto?> = listOf()

) : Serializable