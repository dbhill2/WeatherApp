package com.example.weatherapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.example.weatherapp.R
import com.example.weatherapp.models.WeatherType
import com.example.weatherapp.util.dateTimeConverter

@Composable
fun DisplayWeeklyWeather(modifier: Modifier = Modifier) {

    val weeklyWeatherList = listOf(
        WeatherType(
            dayOfTheWeek = "Monday",
            name = "Sunny",
            lowTemp = 15,
            highTemp = 26,
            humidity = 15,
            windSpeed = 10,
            resourceId = R.drawable.sun
        ),
        WeatherType(
            dayOfTheWeek = "Tuesday",
            name = "Rainy",
            lowTemp = 10,
            highTemp = 19,
            humidity = 25,
            windSpeed = 16,
            resourceId = R.drawable.rain
        ),
        WeatherType(
            dayOfTheWeek = "Wednesday",
            name = "Cloudy",
            lowTemp = 15,
            highTemp = 23,
            humidity = 98,
            windSpeed = 20,
            resourceId = R.drawable.cloudy
        ),
        WeatherType(
            dayOfTheWeek = "Thursday",
            name = "Partly Cloudy",
            lowTemp = 17,
            highTemp = 28,
            humidity = 76,
            windSpeed = 17,
            resourceId = R.drawable.partlycloudy
        ),
        WeatherType(
            dayOfTheWeek = "Friday",
            name = "Rainy",
            lowTemp = 13,
            highTemp = 19,
            humidity = 80,
            windSpeed = 23,
            resourceId = R.drawable.rain
        ),
        WeatherType(
            dayOfTheWeek = "Saturday",
            name = "Sunny",
            lowTemp = 20,
            highTemp = 31,
            humidity = 35,
            windSpeed = 14,
            resourceId = R.drawable.sun
        ),
        WeatherType(
            dayOfTheWeek = "Sunday",
            name = "Cloudy",
            lowTemp = 15,
            highTemp = 26,
            humidity = 56,
            windSpeed = 45,
            resourceId = R.drawable.cloudy
        ),
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ){
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(weeklyWeatherList) {index, weather ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    Image(
                        painter = painterResource(weather.resourceId),
                        contentDescription = "Picture of ${weather.name} weather",
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.width(160.dp)
                    ) {
                        Text(
                            text = weather.dayOfTheWeek,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(weather.name)
                        Text("${weather.lowTemp}c - ${weather.highTemp}c")
                        Text("Humidity: ${weather.humidity}%")
                        Text("Wind: ${weather.windSpeed}kmh")
                    }
                    }
                if (index < weeklyWeatherList.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }
        }
    }
}