package com.example.weatherapp.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun dateTimeConverter(): String{
    val currentDateTime: ZonedDateTime = ZonedDateTime.now();
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd HH:mm z");
    val formattedDateTime = currentDateTime.format(formatter);
    return formattedDateTime
}
//fun dayOfWeekConverter(): String {
//    val currentDateTime = ZonedDateTime.now()
//    val formatter = DateTimeFormatter.ofPattern("EEEE")
//    return currentDateTime.format(formatter)
//}