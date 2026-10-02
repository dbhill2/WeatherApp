package com.example.weatherapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import com.example.weatherapp.util.dateTimeConverter

@Composable
fun DisplayDailyWeather(modifier: Modifier = Modifier) {

    val weatherList = listOf(
        WeatherType(
            dayOfTheWeek = "EEEE",
            name = "Sunny",
            temp = "26c",
            resourceId = R.drawable.sunbaby
        ),
    )
    var timeText by remember { mutableStateOf(dateTimeConverter()) }

    LaunchedEffect(Unit) {
        while(isActive){
            timeText = dateTimeConverter();
            val now = ZonedDateTime.now();
            val secondsToNextMin  = 60 - now.second;
            val nanosToNextMin = (secondsToNextMin * 1_000L) - (now.nano/1_000_000L)
            delay(nanosToNextMin.milliseconds);
        }
    }
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
            weatherList.forEach { weather ->
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier

                ) {
                    Image(
                        painter = painterResource(weather.resourceId),
                        contentDescription = "Picture of ${weather.name} weather",
                        modifier = Modifier
                            .size(200.dp),
                    )
                    Text(
                        text = timeText,
                    )
                    Text(
                        weather.name + " " + weather.temp
                    )
                }
            }
        }
    }
}
