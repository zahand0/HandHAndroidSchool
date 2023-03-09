package com.zahand0.weather.presentation.ui.screen.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zahand0.weather.domain.util.Resource
import com.zahand0.weather.data.repository.WeatherRepositoryImpl
import com.zahand0.weather.domain.model.WeatherModel
import com.zahand0.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel : ViewModel() {

    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()

    private val _weatherModel: MutableStateFlow<WeatherModel?> = MutableStateFlow(null)
    val weatherModel: StateFlow<WeatherModel?> = _weatherModel

    fun updateTemperature(locationName: String, onError: (String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val weather = weatherRepository.getWeatherData(locationName)) {
                is Resource.Error -> {
                    withContext(Dispatchers.Main) {
                        onError(weather.message)
                    }
                }
                is Resource.Success -> {
                    _weatherModel.value = weather.data
                }
            }
        }
    }
}