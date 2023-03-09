package com.zahand0.weather.data.repository

import android.util.Log
import com.zahand0.weather.domain.util.Resource
import com.zahand0.weather.data.remote.WeatherApi
import com.zahand0.weather.data.mappers.toWeatherModel
import com.zahand0.weather.domain.model.WeatherModel
import com.zahand0.weather.domain.repository.WeatherRepository
import com.zahand0.weather.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class WeatherRepositoryImpl : WeatherRepository {

    private val weatherApi: WeatherApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    override suspend fun getWeatherData(locationName: String): Resource<WeatherModel> {
        return try {
            Resource.Success(
                data = weatherApi.getWeatherData(locationName).toWeatherModel()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error during loading data from api", e )
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    companion object {
        private const val TAG = "WeatherRepositoryImpl"
    }
}