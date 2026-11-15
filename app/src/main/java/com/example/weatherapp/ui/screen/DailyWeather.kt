package com.example.weatherapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import coil3.compose.AsyncImage
import com.example.weatherapp.MainViewModel
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.icons.filled.Lightbulb
//import com.example.weatherapp.data.dao.WeatherResponse
//import com.example.weatherapp.models.WeatherType
//import com.example.weatherapp.ui.component.WeatherTips
import com.example.weatherapp.util.dayOfWeekConverter
import com.example.weatherapp.util.weatherTip
import kotlin.math.roundToInt

@Composable
fun DisplayDailyWeather(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    //STORES THE DAY OF THE WEEK FROM THE UTILITY OVER RECOMPOSITIONS.
    //UPDATES TEXT WHEN THE CLOCK STRIKES MIDNIGHT
    val tzId = viewModel.weather?.location?.tzId
    var dayOfTheWeekText by remember(tzId) { mutableStateOf(dayOfWeekConverter(tzId)) }

    //THIS EFFECT KEEPS TRACK OF THE CLOCK SO OUR DAY CAN CHANGE AT MIDNIGHT
    //USED TO DISPLAY TIME ON NAV BAR TOO
    //RUNS FOR AS LONG AS THE PAGE IS IN COMPOSITION
    LaunchedEffect(tzId) {
        while (isActive) {
            dayOfTheWeekText = dayOfWeekConverter(tzId)
            val now = ZonedDateTime.now()
            val millisToNextMin = ((60 - now.second) * 1_000L) - (now.nano / 1_000_000L)
            delay(millisToNextMin.milliseconds)
        }
    }
    val weather = viewModel.weather

    weather?.let{
        val current = weather.current
        val today = weather.forecast.forecastDays.first().day
        val tip = weatherTip(current)
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                ){
                    Text(dayOfTheWeekText, style = MaterialTheme.typography.headlineMedium)
                    AsyncImage(
                        model = "https:${current.condition.icon.replace("64x64", "128x128")}",
                        contentDescription = current.condition.weatherType,
                        modifier = Modifier.size(128.dp)
                    )
                    Text(
                        "${current.tempCel.roundToInt()}°",
                        style = MaterialTheme.typography.displayMedium
                    )
                    Text(
                        current.condition.weatherType,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "High ${today.maxCel.roundToInt()}° · Low ${today.minCel.roundToInt()}°",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                InfoCard(
                    icon = Icons.Filled.Thermostat,
                    title = "Feels like",
                    value = "${current.feelsLike.roundToInt()}°",
                    detail = "Actual ${current.tempCel.roundToInt()}°",
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                InfoCard(
                    icon = Icons.Filled.Air,
                    title = "Wind",
                    value = "${current.windKph.roundToInt()} km/h",
                    detail = current.windDirection,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                InfoCard(
                    icon = Icons.Filled.WaterDrop,
                    title = "Rain",
                    value = "${current.chanceOfRain.roundToInt()}%",
                    detail = "${current.precipMili} mm",
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
                InfoCard(
                    icon = Icons.Filled.Opacity,
                    title = "Humidity",
                    value = "${current.humidity.roundToInt()}%",
                    detail = "",
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ){
                InfoCard(
                    icon = tip.icon,
                    title = "Daily Weather Tip",
                    detail = tip.text,
                    modifier = Modifier.weight(1f).fillMaxHeight()
                )
            }
        }// end Column
    }// end weather?.let
}// end DisplayDailyWeather
@Composable
private fun InfoCard(
    icon: ImageVector,
    title: String,
    detail: String,
    modifier: Modifier = Modifier,
    value: String = "",
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                Text(title, style = MaterialTheme.typography.labelLarge)
            }
            if (value.isNotBlank()){
                Text(value, style = MaterialTheme.typography.headlineSmall)
            }
            if (detail.isNotBlank()) {
                Text(
                    detail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}