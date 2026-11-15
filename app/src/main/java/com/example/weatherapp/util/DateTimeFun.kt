package com.example.weatherapp.util

import java.time.DateTimeException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private fun zoneFor(tzId: String?): ZoneId =
    try {
        if (tzId.isNullOrBlank()) ZoneId.systemDefault() else ZoneId.of(tzId)
    } catch (e: DateTimeException) {
        ZoneId.systemDefault()
    }
fun dateTimeConverter(tzId: String? = null): String {
    val currentDateTime = ZonedDateTime.now(zoneFor(tzId))
    val formatter = DateTimeFormatter.ofPattern("EEEE, MMM dd HH:mm z")
    return currentDateTime.format(formatter)
}

fun dayOfWeekConverter(tzId: String? = null): String {
    val currentDateTime = ZonedDateTime.now(zoneFor(tzId))
    val formatter = DateTimeFormatter.ofPattern("EEEE")
    return currentDateTime.format(formatter)
}