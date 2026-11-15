package com.example.weatherapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Opacity
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.data.dao.ForecastDay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun DisplayWeeklyWeather(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val weather = viewModel.weather

    if (weather != null) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(weather.forecast.forecastDays, key = { _, d -> d.date }) { index, forecastDay ->
                DayCard(forecastDay = forecastDay, isToday = index == 0)
            }
        }
    }
}

@Composable
private fun DayCard(forecastDay: ForecastDay, isToday: Boolean) {
    val day = forecastDay.day
    val date = LocalDate.parse(forecastDay.date)
    val dayName = if (isToday) "Today"
    else date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    val dateText = date.format(DateTimeFormatter.ofPattern("MMM d", Locale.getDefault()))

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Top: icon, day/condition, high/low
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = "https:${day.condition.icon}",
                    contentDescription = day.condition.weatherType,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(dayName, style = MaterialTheme.typography.titleLarge)
                    Text(
                        "$dateText · ${day.condition.weatherType}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "${day.maxCel.roundToInt()}°",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        "${day.minCel.roundToInt()}°",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider()

            // Bottom: rain, wind, humidity
            Row(modifier = Modifier.fillMaxWidth()) {
                Stat(
                    icon = Icons.Filled.WaterDrop,
                    value = "${day.dailyChanceOfRain.roundToInt()}%",
                    label = "${day.totalPrecipMm} mm",
                    modifier = Modifier.weight(1f)
                )
                Stat(
                    icon = Icons.Filled.Air,
                    value = "${day.maxWindKph.roundToInt()} km/h",
                    label = "Max wind",
                    modifier = Modifier.weight(1f)
                )
                Stat(
                    icon = Icons.Filled.Opacity,
                    value = "${day.avgHumidity.roundToInt()}%",
                    label = "Humidity",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun Stat(icon: ImageVector, value: String, label: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
        Text(value, style = MaterialTheme.typography.titleSmall)
        Text(
            label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}