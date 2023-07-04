package net.zuuno.component.weather.data.mapper

import net.zuuno.component.weather.data.local.model.WeatherDataCached
import net.zuuno.component.weather.data.remote.model.WeatherResponse
import net.zuuno.component.weather.domain.model.Weather
import net.zuuno.component.weather.domain.model.WeatherData

fun WeatherDataCached.toDomainModel() = WeatherData(
    id = id,
    location = location,
    weather = Weather.valueOf(weather),
    temperature = temperature,
)

fun WeatherResponse.toDomainModel() = WeatherData(
    id = "0",
    location = "Tokyo",
    weather = Weather.SUNNY,
    temperature = currentWeather.temperature,
)

fun WeatherData.toEntityModel() = WeatherDataCached(
    id = id,
    location = location,
    weather = weather.name,
    temperature = temperature,
)