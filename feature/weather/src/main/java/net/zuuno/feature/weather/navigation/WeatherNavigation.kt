package net.zuuno.feature.weather.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import net.zuuno.feature.weather.WeatherScreen

const val WEATHER_ROUTE = "weather"

fun NavController.navigateToWeather() {
    this.navigate(WEATHER_ROUTE) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.weatherScreen() {
    composable(WEATHER_ROUTE) {
        WeatherScreen()
    }
}