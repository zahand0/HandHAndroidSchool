package com.zahand0.weather.data.remote

import com.zahand0.weather.data.dto.WeatherDto
import com.zahand0.weather.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather?units=metric&appid=${Constants.OPEN_WEATHER_API_KEY}")
    suspend fun getWeatherData(
        @Query("q") locationName: String,
    ): WeatherDto
}