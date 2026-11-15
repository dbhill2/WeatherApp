package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.data.dao.isOvercast
import com.example.weatherapp.data.dao.isRainy
import com.example.weatherapp.data.dao.isSnowy
import com.example.weatherapp.ui.TopAppBar
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.ui.theme.WeatherThemeMode
import com.example.weatherapp.ui.theme.toThemeMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel()
            val current = viewModel.weather?.current
            val mode = viewModel.weather?.current?.toThemeMode() ?: WeatherThemeMode.SUNNY

            WeatherAppTheme(mode = mode) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TopAppBar(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

