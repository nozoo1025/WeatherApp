package net.zuuno.component.weather.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyUnits(
    @SerialName("time")
    val time: String,
    @SerialName("weathercode")
    val weathercode: String
)