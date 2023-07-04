package net.zuuno.feature.weather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherDataDisplayable(
    val id: String,
    val location: String,
    val weather: String,
    val temperature: Double,
) : Parcelable