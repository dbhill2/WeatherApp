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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
            temp = "26c",
            resourceId = R.drawable.sunbaby
        ),
        WeatherType(
            dayOfTheWeek = "Tuesday",
            name = "Rainy",
            temp = "15c",
            resourceId = R.drawable.raincloud
        ),
        WeatherType(
            dayOfTheWeek = "Wednesday",
            name = "Cloudy",
            temp = "18c",
            resourceId = R.drawable.cloudy
        ),
        WeatherType(
            dayOfTheWeek = "Thursday",
            name = "Partly Cloudy",
            temp = "20c",
            resourceId = R.drawable.partlycloudy
        ),
        WeatherType(
            dayOfTheWeek = "Friday",
            name = "Rainy",
            temp = "26c",
            resourceId = R.drawable.raincloud
        ),
        WeatherType(
            dayOfTheWeek = "Saturday",
            name = "Sunny",
            temp = "26c",
            resourceId = R.drawable.sunbaby
        ),
        WeatherType(
            dayOfTheWeek = "Sunday",
            name = "Cloudy",
            temp = "26c",
            resourceId = R.drawable.cloudy
        ),
    )
    var timeText by remember { mutableStateOf(dateTimeConverter()) }

    LaunchedEffect(Unit) {
        while(isActive){
            timeText = dateTimeConverter();
            val now = ZonedDateTime.now();
            val secondsToNextMin  = 60 - now.second;
            val millisToNextMin = (secondsToNextMin * 1_000L) - (now.nano/1_000_000L)
            delay(millisToNextMin.milliseconds);
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ){
        Text(
            text = timeText,
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(weeklyWeatherList) { weather ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    Box(
                        modifier = Modifier.size(100.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = painterResource(weather.resourceId),
                            contentDescription = "Picture of ${weather.name} weather",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(weather.dayOfTheWeek)
                        Text(weather.name)
                        Text(weather.temp)
                    }
                }
            }
        }
    }
}