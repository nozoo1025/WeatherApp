package net.zuuno.component.weather.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import net.zuuno.component.weather.data.local.dao.WeatherDataDao
import net.zuuno.component.weather.data.mapper.toDomainModel
import net.zuuno.component.weather.data.mapper.toEntityModel
import net.zuuno.component.weather.data.remote.api.WeatherApi
import net.zuuno.component.weather.domain.model.WeatherData
import net.zuuno.component.weather.domain.repository.WeatherDataRepository
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val weatherDataDao: WeatherDataDao,
) : WeatherDataRepository {

    override fun getWeatherData(): Flow<WeatherData?> {
        return weatherDataDao
            .getWeatherData()
            .map { weatherDataCached ->
                weatherDataCached?.toDomainModel()
            }
            .onEach { weather ->
                if (weather == null) {
                    refreshWeatherData()
                }
            }
    }

    override suspend fun refreshWeatherData() {
        weatherApi
            .getWeather()
            .toDomainModel()
            .toEntityModel()
            .also {
                weatherDataDao.saveWeatherData(it)
            }
    }
}