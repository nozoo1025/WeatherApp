package net.zuuno.component.weather.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherDataCached(

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "location")
    val location: String,

    @ColumnInfo(name = "weather")
    val weather: String,

    @ColumnInfo(name = "temperature")
    val temperature: Double,
)
