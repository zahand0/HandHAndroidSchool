package com.zahand0.weather.data.dto

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)