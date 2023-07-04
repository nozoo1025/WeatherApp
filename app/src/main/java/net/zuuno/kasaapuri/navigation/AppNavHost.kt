package net.zuuno.kasaapuri.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import net.zuuno.feature.weather.navigation.WEATHER_ROUTE
import net.zuuno.feature.weather.navigation.weatherScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController,
) {
    NavHost(
        navController = navHostController,
        startDestination = WEATHER_ROUTE,
    ) {
        weatherScreen()
    }
}