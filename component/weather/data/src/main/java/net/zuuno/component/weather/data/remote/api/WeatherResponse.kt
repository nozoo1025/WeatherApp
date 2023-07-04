package net.zuuno.component.weather.data.remote.api

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val id: Int,
)