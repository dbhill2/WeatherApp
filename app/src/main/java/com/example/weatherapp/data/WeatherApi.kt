package com.example.weatherapp.data

import com.example.weatherapp.BuildConfig.WEATHER_API_KEY
import com.example.weatherapp.data.dao.SearchResult
import com.example.weatherapp.data.dao.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast.json")
    suspend fun getWeather(
        @Query("key") key: String = WEATHER_API_KEY,
        @Query("q") q: String,
        @Query("days") days: String = "3",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): Weather

    @GET("v1/search.json")
    suspend fun searchLocations(
        @Query("key") key: String = WEATHER_API_KEY,
        @Query("q") q: String
    ): List<SearchResult>
}