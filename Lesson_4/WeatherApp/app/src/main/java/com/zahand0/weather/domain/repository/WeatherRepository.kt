package com.zahand0.weather.domain.repository

import com.zahand0.weather.domain.util.Resource
import com.zahand0.weather.domain.model.WeatherModel

interface WeatherRepository {

    suspend fun getWeatherData(locationName: String): Resource<WeatherModel>

}