package net.zuuno.component.weather.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Daily(
    @SerialName("time")
    val time: List<String>,
    @SerialName("weathercode")
    val weathercode: List<Int>
)