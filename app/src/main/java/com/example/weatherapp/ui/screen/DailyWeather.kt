package com.example.weatherapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.weatherapp.R
import com.example.weatherapp.models.WeatherType
//import com.example.weatherapp.ui.component.WeatherTips
import com.example.weatherapp.util.dateTimeConverter
import com.example.weatherapp.util.dayOfWeekConverter
import kotlin.compareTo

@Composable
fun DisplayDailyWeather(modifier: Modifier = Modifier) {

    var dayOfTheWeekText by remember { mutableStateOf(dayOfWeekConverter()) }

    LaunchedEffect(Unit) {
        while(isActive){
            dayOfTheWeekText = dayOfWeekConverter();
            val now = ZonedDateTime.now();
            val secondsToNextMin  = 60 - now.second;
            val millisToNextMin = (secondsToNextMin * 1_000L) - (now.nano/1_000_000L)
            delay(millisToNextMin.milliseconds);
        }
    }
    val weekArray = arrayOf(
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday",
    )
    val weeklyWeatherList = listOf(
        WeatherType(
            dayOfTheWeek = weekArray[0],
            name = "Sunny",
            lowTemp = 15,
            highTemp = 26,
            humidity = 15,
            windSpeed = 10,
            resourceId = R.drawable.sun
        ),
        WeatherType(
            dayOfTheWeek = weekArray[1],
            name = "Rainy",
            lowTemp = 10,
            highTemp = 19,
            humidity = 25,
            windSpeed = 16,
            resourceId = R.drawable.rain
        ),
        WeatherType(
            dayOfTheWeek = weekArray[2],
            name = "Cloudy",
            lowTemp = 15,
            highTemp = 23,
            humidity = 98,
            windSpeed = 20,
            resourceId = R.drawable.cloudy
        ),
        WeatherType(
            dayOfTheWeek = weekArray[3],
            name = "Partly Cloudy",
            lowTemp = 17,
            highTemp = 28,
            humidity = 76,
            windSpeed = 17,
            resourceId = R.drawable.partlycloudy
        ),
        WeatherType(
            dayOfTheWeek = weekArray[4],
            name = "Rainy",
            lowTemp = 13,
            highTemp = 19,
            humidity = 80,
            windSpeed = 23,
            resourceId = R.drawable.rain
        ),
        WeatherType(
            dayOfTheWeek = weekArray[5],
            name = "Sunny",
            lowTemp = 20,
            highTemp = 31,
            humidity = 35,
            windSpeed = 14,
            resourceId = R.drawable.sun
        ),
        WeatherType(
            dayOfTheWeek = weekArray[6],
            name = "Cloudy",
            lowTemp = 10,
            highTemp = 24,
            humidity = 56,
            windSpeed = 45,
            resourceId = R.drawable.cloudy
        ),
    )

    val todayIndex = weeklyWeatherList
        .indexOfFirst { it.dayOfTheWeek.equals(dayOfTheWeekText, ignoreCase = true) }
        .coerceAtLeast(0) // fall back to the original order if there's no match

    val weather = weeklyWeatherList[todayIndex]

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                )
        {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier

            ) {
                Text(
                    text = dayOfTheWeekText,
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier.height(16.dp))
                Image(
                    painter = painterResource(weather.resourceId),
                    contentDescription = "Picture of ${weather.name} weather",
                    modifier = Modifier
                        .size(200.dp),
                )
                Spacer(modifier.height(16.dp))
                Text(
                    ("${ weather.name } ${weather.lowTemp}c - ${weather.highTemp}c")
                )
                Text("Humidity: ${weather.humidity}%, Windspeed: ${weather.windSpeed}kmh")
                if(weather.highTemp >= 20 && weather.name == "Sunny"){
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                    ){
                        Text("It's a perfect day for the beach!\nDon't forget your sunscreen!!")
                        Image(
                            painter = painterResource(R.drawable.sunblock),
                            contentDescription = "Picture of sunblock",
                            modifier = Modifier.size(50.dp)
                        )
                    }
                }
                if(weather.name == "Rainy"){

                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Don't forget your umbrella!!")
                    Image(
                        painter = painterResource(R.drawable.umbrella),
                        contentDescription = "Picture of an umbrella",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}
