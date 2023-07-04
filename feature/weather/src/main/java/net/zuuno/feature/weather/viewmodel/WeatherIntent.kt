package net.zuuno.feature.weather.viewmodel

sealed class WeatherIntent {
    object GetWeatherData : WeatherIntent()
    object RefreshWeatherData : WeatherIntent()
}