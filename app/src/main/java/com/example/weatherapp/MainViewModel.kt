package com.example.weatherapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.dao.Weather
import com.example.weatherapp.data.WeatherApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.weatherapp.data.dao.SearchResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

class MainViewModel(app: Application) : AndroidViewModel(app) {
    var weather by mutableStateOf<Weather?>(null)
        private set

    // Name of the device's GPS location (independent of which location is displayed)
    var deviceLocationName by mutableStateOf<String?>(null)
        private set

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(app)
    var location: Location? by mutableStateOf<Location?>(null) // NOTE: import Android's built-in Location class  android.location.Location
        private set

    // ---- Saved locations ----
    private val prefs = app.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    val savedLocations = mutableStateListOf<String>().apply { addAll(loadSaved()) }

    var searchResults by mutableStateOf<List<SearchResult>>(emptyList())
        private set
    private var searchJob: Job? = null

    fun searchLocations(query: String) {
        searchJob?.cancel()
        if (query.trim().length < 3) {
            searchResults = emptyList()
            return
        }
        searchJob = viewModelScope.launch {
            delay(400) // debounce so we don't call the API on every keystroke
            try {
                searchResults = api.searchLocations(q = query.trim())
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e("WeatherApp", "searchLocations failed", e)
                searchResults = emptyList()
            }
        }
    }

    fun clearSearch() {
        searchJob?.cancel()
        searchResults = emptyList()
    }
    private fun loadSaved(): List<String> {
        val json = prefs.getString("saved_locations", null) ?: return emptyList()
        return try {
            gson.fromJson(json, Array<String>::class.java).toList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun persistSaved() {
        prefs.edit().putString("saved_locations", gson.toJson(savedLocations.toList())).apply()
    }

    fun saveLocation(name: String) {
        if (name !in savedLocations) {
            savedLocations.add(name)
            persistSaved()
        }
    }

    fun removeLocation(name: String) {
        savedLocations.remove(name)
        persistSaved()
    }
    // -------------------------

    private val api = Retrofit.Builder()
        .baseUrl("https://api.weatherapi.com/") // API hostname
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherApi::class.java) // Interface

    fun getWeather(query: String) {
        viewModelScope.launch {
            try {
                weather = api.getWeather(q = query)
            } catch (e: Exception) {
                android.util.Log.e("WeatherApp", "getWeather failed", e)
            }
        }
    }

    @SuppressLint("MissingPermission") // silence warning
    fun refreshLocation() {
        fusedLocationClient
            .getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
            .addOnSuccessListener { result ->
                location = result
                result?.let{getWeather("${it.latitude},${it.longitude}")}
            }
            .addOnFailureListener { Log.e("MainViewModel", "Location failed", it) }
    }
}