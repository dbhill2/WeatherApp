package com.example.weatherapp.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CalendarViewWeek
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weatherapp.ui.screen.LocationScreen
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.MainViewModel
import com.example.weatherapp.ui.screen.DisplayDailyWeather
import com.example.weatherapp.ui.screen.DisplayWeeklyWeather
import com.example.weatherapp.util.dateTimeConverter
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import java.time.ZonedDateTime
import kotlin.time.Duration.Companion.milliseconds

// default set to 0
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val onLocationScreen = backStackEntry?.destination?.route == "location"
    var selectedIndex by remember { mutableIntStateOf(0) }

    //GETS THE TIME FROM MY UTILITY FUNCTION AND STORES IT READY TO MUTATE EVERY MINUTE
    val viewModel: MainViewModel = viewModel()
    val tzId = viewModel.weather?.location?.tzId
    var timeText by remember(tzId) { mutableStateOf(dateTimeConverter(tzId)) }
    val locationText = viewModel.weather?.location?.let { loc ->
        listOf(loc.name, loc.region).filter { it.isNotBlank() }.joinToString(", ")
    } ?: "Getting location..."

    // Begin: Ask permission to get device location.
    // Note: If permission is previously granted, the dialog won't appear and refreshLocation() will get called.
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        if (results.values.any { it }) {
            viewModel.refreshLocation() // call a function to get the current location for device
        }
    }

    LaunchedEffect(Unit) {
        launcher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
    // End: Ask permission to get device location.

    //UPDATES THE TEXT EVERY MINUTE
    //STAYS RUNNING AS LONG AS THE COMPOSABLE IS OPEN
    LaunchedEffect(tzId) {
        while (isActive) {
            timeText = dateTimeConverter(tzId)
            val now = ZonedDateTime.now()
            val secondsToNextMin = 60 - now.second
            val millisToNextMin = (secondsToNextMin * 1_000L) - (now.nano / 1_000_000L)
            delay(millisToNextMin.milliseconds)
        }
    }
    Scaffold(
        topBar = {
            if(!onLocationScreen){
                TopAppBar(
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        actionIconContentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    title = {
                        Column {
                            Text(
                                text = locationText
                            )
                            Text(
                                text = timeText,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("location") }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                )
            }
        },
        bottomBar = {
            if(!onLocationScreen){
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets)
                {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = "Daily"
                            )
                        },
                        label = { Text("Daily") },
                        selected = selectedIndex == 0,
                        onClick = {
                            selectedIndex = 0
                            navController.navigate(route = "daily") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = Icons.Default.CalendarViewWeek,
                                contentDescription = "Weekly"
                            )
                        },
                        label = { Text("Weekly") },
                        selected = selectedIndex == 1,
                        onClick = {
                            selectedIndex = 1
                            navController.navigate(route = "weekly") {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    )
    { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (onLocationScreen) PaddingValues(0.dp) else innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = "daily",
            ) {
                composable("daily") { DisplayDailyWeather(viewModel = viewModel) }
                composable("weekly") { DisplayWeeklyWeather(Modifier, viewModel) }
                composable("location") {
                    LocationScreen(
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
