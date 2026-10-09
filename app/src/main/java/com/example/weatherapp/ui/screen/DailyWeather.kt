package com.example.weatherapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
//import com.example.weatherapp.ui.component.WeatherTips
import com.example.weatherapp.util.dateTimeConverter
import kotlin.compareTo

@Composable
fun DisplayDailyWeather(modifier: Modifier = Modifier) {

    val weatherList = listOf(
        WeatherType(
            dayOfTheWeek = "EEEE",
            name = "Sunny",
            lowTemp = 15,
            highTemp = 26,
            humidity = 15,
            windSpeed = 10,
            resourceId = R.drawable.sun
        ),
    )
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
                        ("${ weather.name } ${weather.lowTemp}c - ${weather.highTemp}c")
                    )
                    Text("Humidity: ${weather.humidity}%, Windspeed: ${weather.windSpeed}kmh")
                    if(weather.highTemp >= 20 && weather.name == "Sunny"){
                        Spacer(modifier = Modifier.height(24.dp))
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                        ){
                            Text("It's a perfect day for the beach!\n\nDon't forget your sunscreen!!")
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
}
