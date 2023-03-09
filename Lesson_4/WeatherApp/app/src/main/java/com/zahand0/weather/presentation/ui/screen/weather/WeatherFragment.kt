package com.zahand0.weather.presentation.ui.screen.weather

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.zahand0.weather.R
import com.zahand0.weather.databinding.FragmentWeatherBinding
import kotlinx.coroutines.flow.collectLatest

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding

    private val viewModel by activityViewModels<WeatherViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initTextLocation()
        lifecycleScope.launchWhenResumed {
            viewModel.weatherModel.collectLatest { weatherModel ->
                weatherModel?.let {
                    binding.textTemperature.text = getString(
                        R.string.weather_data,
                        weatherModel.locationName,
                        weatherModel.temperature.toInt()
                    )
                }
            }
        }
    }

    private fun searchLocation() {
        viewModel.updateTemperature(
            locationName = binding.editTextLocation.text.toString(),
            onError = {
                Toast.makeText(
                    requireContext(),
                    it ?: getString(R.string.unknown_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    private fun initTextLocation() {
        binding.editTextLocation.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                event.action == KeyEvent.ACTION_DOWN &&
                event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                searchLocation()
                true
            } else {
                false
            }
        }
    }
}