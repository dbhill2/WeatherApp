package com.example.weatherapp.models

data class WeatherType (
    val dayOfTheWeek: String,
    val name: String,
    val lowTemp: Int,
    val highTemp: Int,
    val humidity: Int,
    val windSpeed: Int,
    val resourceId: Int
)