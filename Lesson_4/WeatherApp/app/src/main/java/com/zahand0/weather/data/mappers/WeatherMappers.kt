package com.zahand0.weather.data.mappers

import com.zahand0.weather.data.dto.WeatherDto
import com.zahand0.weather.domain.model.WeatherModel

fun WeatherDto.toWeatherModel(): WeatherModel {
    return WeatherModel(
        locationName = this.name,
        temperature = this.main.temp
    )
}
