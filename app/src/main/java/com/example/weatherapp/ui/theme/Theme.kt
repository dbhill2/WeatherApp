package com.example.weatherapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.weatherapp.data.dao.Current
import com.example.weatherapp.data.dao.isNight
import com.example.weatherapp.data.dao.isOvercast
import com.example.weatherapp.data.dao.isRainy
import com.example.weatherapp.data.dao.isSnowy

private val DarkColorScheme = darkColorScheme(
    primary = SunGold,
    secondary = WarmOrange,
    primaryContainer = SunGold,
    onPrimaryContainer = Charcoal,
    background = Cream,
    onBackground = Charcoal,
    onSurface = Charcoal,
)

private val LightColorScheme = lightColorScheme(
    primary = SunGold,
    secondary = WarmOrange,
    primaryContainer = SunGold,
    onPrimaryContainer = Charcoal,
    background = Cream,
    onBackground = Charcoal,
    onSurface = Charcoal,
)
private val CloudyColorScheme = lightColorScheme(
    primary = CloudSlate,
    secondary = CloudSteel,
    primaryContainer = CloudContainer,
    onPrimaryContainer = StormInk,
    background = CloudMist,
    onBackground = StormInk,
    surface = CloudMist,
    onSurface = StormInk,
)

private val RainyColorScheme = darkColorScheme(
    primary = RainBlue,
    onPrimary = RainNavy,
    secondary = RainTeal,
    onSecondary = RainNavy,
    primaryContainer = RainContainer,
    onPrimaryContainer = RainMist,
    background = RainNavy,
    onBackground = RainMist,
    surface = RainSurface,
    onSurface = RainMist,
    surfaceContainer = RainSurface,
)

private val SnowyColorScheme = lightColorScheme(
    primary = GlacierBlue,
    onPrimary = SnowWhite,
    secondary = IceBlue,
    onSecondary = SnowSlate,
    primaryContainer = IceBlue,
    onPrimaryContainer = SnowSlate,
    background = SnowWhite,
    onBackground = SnowSlate,
    surface = SnowWhite,
    onSurface = SnowSlate,
    surfaceContainer = SnowFrost,
)

private val NightColorScheme = darkColorScheme(
    primary = MoonBlue,
    onPrimary = NightIndigo,
    secondary = StarGold,
    onSecondary = NightIndigo,
    primaryContainer = NightContainer,
    onPrimaryContainer = NightText,
    background = NightIndigo,
    onBackground = NightText,
    surface = NightSurface,
    onSurface = NightText,
    surfaceContainer = NightSurface,
)
enum class WeatherThemeMode { SUNNY, CLOUDY, RAINY, SNOWY, NIGHT }

fun Current.toThemeMode(): WeatherThemeMode = when {
    isSnowy -> WeatherThemeMode.SNOWY
    isRainy -> WeatherThemeMode.RAINY
    isOvercast -> WeatherThemeMode.CLOUDY
    isNight -> WeatherThemeMode.NIGHT
    else -> WeatherThemeMode.SUNNY
}
@Composable
fun WeatherAppTheme(
    mode: WeatherThemeMode = WeatherThemeMode.SUNNY,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        mode == WeatherThemeMode.SNOWY -> SnowyColorScheme
        mode == WeatherThemeMode.RAINY -> RainyColorScheme
        mode == WeatherThemeMode.CLOUDY -> CloudyColorScheme
        mode == WeatherThemeMode.NIGHT -> NightColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars =
                mode != WeatherThemeMode.RAINY && mode != WeatherThemeMode.NIGHT
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}