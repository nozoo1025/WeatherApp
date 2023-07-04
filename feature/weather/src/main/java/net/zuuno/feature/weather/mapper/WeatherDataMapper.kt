package net.zuuno.feature.weather.mapper

import net.zuuno.component.weather.domain.model.Weather
import net.zuuno.component.weather.domain.model.WeatherData
import net.zuuno.feature.weather.model.WeatherDataDisplayable

fun WeatherData.toPresentationModel() = WeatherDataDisplayable(
    id = id,
    location = location,
    weather = weather.toReadable(),
    temperature = temperature,
)

private fun Weather.toReadable() = this.name