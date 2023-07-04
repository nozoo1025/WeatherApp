package net.zuuno.component.weather.domain.repository

import kotlinx.coroutines.flow.Flow
import net.zuuno.component.weather.domain.model.WeatherData

interface WeatherDataRepository {

    fun getWeatherData(): Flow<WeatherData>

    suspend fun refreshWeatherData()
}