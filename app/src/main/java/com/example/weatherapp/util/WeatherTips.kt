package com.example.weatherapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.weatherapp.data.dao.Current
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbSunny
import com.example.weatherapp.data.dao.IsBlizzard
import com.example.weatherapp.data.dao.isRainy
import com.example.weatherapp.data.dao.isSnowy
import com.example.weatherapp.data.dao.isSunny

data class WeatherTip(val icon: ImageVector, val text: String)
fun weatherTip(current: Current): WeatherTip = when{
    current.isSnowy && current.isRainy ->
        WeatherTip(Icons.Filled.DirectionsCar, "It is slippery out there. Make sure to get your snow tires on if they aren't already!")
    current.IsBlizzard ->
        WeatherTip(Icons.Filled.AcUnit,"I hope you didn't forget your Stormchips!")
    current.isSnowy ->
        WeatherTip(Icons.Filled.AcUnit, "Bundle up. It's snowing today!")
    current.tempCel <= 0 ->
        WeatherTip(Icons.Filled.Coffee, "It's cold out there. Warm up with a cuppa.")
    current.isRainy ->
        WeatherTip(Icons.Filled.Umbrella,"Don't forget your umbrella!")
    current.isSunny && current.tempCel >= 20 ->
        WeatherTip(Icons.Filled.WbSunny,"Don't forget sunscreen today!")
    else -> {
        WeatherTip(Icons.Filled.Lightbulb,"Enjoy your day!")
    }
}