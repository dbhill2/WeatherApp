//package com.example.weatherapp.ui.screen
//
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.Tab
//import androidx.compose.material3.TabRow
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//
//enum class WeatherScreen {
//    DAILY,WEEKLY
//}
//
//@Composable
//fun MainScreen(modifier: Modifier = Modifier){
//    var currentScreen by remember { mutableStateOf(WeatherScreen.DAILY) }
//    Column(
//        modifier = modifier.fillMaxSize()
//    ){
//        TabRow(selectedTabIndex = currentScreen.ordinal) {
//            Tab(
//                selected = currentScreen == WeatherScreen.DAILY,
//                onClick = {currentScreen = WeatherScreen.DAILY},
//                text = {Text("Daily")}
//            )
//            Tab(
//                selected = currentScreen == WeatherScreen.WEEKLY,
//                onClick = {currentScreen = WeatherScreen.WEEKLY},
//                text = {Text("Weekly")}
//            )
//        }
//        when (currentScreen) {
//            WeatherScreen.DAILY -> DisplayDailyWeather()
//            WeatherScreen.WEEKLY -> DisplayWeeklyWeather()
//        }
//    }
//}