package com.example.weatherapp.data.dao

import com.google.gson.annotations.SerializedName

data class Weather(
    val location: Location,
    val current: Current,
    val forecast: Forecast,
)

data class Location(
    val name: String,
    val region: String,
    @SerializedName("tz_id") val tzId: String?,
)

data class Current(
    val humidity: Float,
    val condition: Condition,
    @SerializedName("temp_c") val tempCel: Float,
    @SerializedName("wind_kph") val windKph: Float,
    @SerializedName("feelslike_c") val feelsLike: Float,
    @SerializedName("wind_dir") val windDirection: String,
    @SerializedName("chance_of_rain") val chanceOfRain: Float,
    @SerializedName("precip_mm") val precipMili: Float,
    @SerializedName("is_day") val isDay: Int?,
)

data class Forecast(
    @SerializedName("forecastday")val forecastDays: List<ForecastDay>
)

data class ForecastDay(
    val date: String,
    val day: Day,
)

data class Day(
    val condition: Condition,
    @SerializedName("maxtemp_c") val maxCel: Float,
    @SerializedName("mintemp_c")val minCel: Float,
    @SerializedName("daily_chance_of_rain")val dailyChanceOfRain: Float,
    @SerializedName("maxwind_kph") val maxWindKph: Float,
    @SerializedName("totalprecip_mm") val totalPrecipMm: Float,
    @SerializedName("avghumidity") val avgHumidity: Float,
)

data class Condition(
    val icon: String,
    val code: Int,
    @SerializedName("text")val weatherType: String,
)

private val RAIN_CODES = setOf(
    1063, 1087,                         // patchy rain possible, thundery outbreaks
    1150, 1153, 1168, 1171,             // drizzle, freezing drizzle
    1180, 1183, 1186, 1189, 1192, 1195, // light to heavy rain
    1198, 1201,                         // freezing rain
    1240, 1243, 1246,                   // rain showers
    1273, 1276                          // thunder with rain
)

private val SNOW_CODES = setOf(
    1066, 1069, 1072,                   // patchy snow / sleet / freezing drizzle possible
    1114, 1117,                         // blowing snow, blizzard
    1204, 1207,                         // light and moderate/heavy sleet
    1210, 1213, 1216, 1219, 1222, 1225, // patchy to heavy snow
    1237,                               // ice pellets
    1249, 1252,                         // sleet showers
    1255, 1258,                         // snow showers
    1261, 1264,                         // ice pellet showers
    1279, 1282                          // thunder with snow
)

private val BLOWING_SNOW_CODE = setOf(
    1114, 1117
)
//HELPERS
val Current.isSunny: Boolean
    get() = condition.code == 1000 && isDay != 0
val Current.IsBlizzard: Boolean
    get() = condition.code in BLOWING_SNOW_CODE
val Current.isRainy: Boolean
    get() = condition.code in RAIN_CODES
val Current.isSnowy: Boolean
    get() = condition.code in SNOW_CODES
val Current.isNight: Boolean
    get() = isDay == 0
data class SearchResult(
    val id: Long,
    val name: String,
    val region: String,
    val country: String,
)

// What gets saved and passed back to the API as `q`

val SearchResult.saveName: String
    get() = listOf(name, region.ifBlank { country }).filter { it.isNotBlank() }.joinToString(", ")

val Location.displayName: String
    get() = listOf(name, region).filter { it.isNotBlank() }.joinToString(", ")
val Current.isOvercast: Boolean
    get() = condition.code in setOf(1006, 1009, 1030, 1135, 1147)