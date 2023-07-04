package net.zuuno.component.weather.data.mapper

import net.zuuno.component.weather.data.local.model.WeatherDataCached
import net.zuuno.component.weather.domain.model.Weather
import net.zuuno.component.weather.domain.model.WeatherData

fun WeatherDataCached.toDomainModel() = WeatherData(
    id = id,
    location = location,
    weather = Weather.valueOf(weather),
    temperature = temperature,
)