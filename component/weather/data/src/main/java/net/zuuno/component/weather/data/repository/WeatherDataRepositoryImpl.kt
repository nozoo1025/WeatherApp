package net.zuuno.component.weather.data.repository

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import net.zuuno.component.weather.domain.model.Weather
import net.zuuno.component.weather.domain.model.WeatherData
import net.zuuno.component.weather.domain.repository.WeatherDataRepository
import java.util.UUID
import javax.inject.Inject

class WeatherDataRepositoryImpl @Inject constructor(
//    private val weatherDataDao: WeatherDataDao,
) : WeatherDataRepository {

    override fun getWeatherData(): Flow<WeatherData> {
//        return weatherDataDao
//            .getWeatherData()
//            .map { weatherCached ->
//                weatherCached.map { it.toDomainModel() }
//            }
//            .onEach { weather ->
//                if (weather.isEmpty()) {
//                    refreshWeatherData()
//                }
//            }
        return flow {
            delay(5000)
            emit(
                WeatherData(
                    id = UUID.randomUUID().toString(),
                    location = "Tokyo",
                    weather = Weather.SUNNY,
                    temperature = 25.0,
                )

            )
        }
    }

    override suspend fun refreshWeatherData() {
        // no-op
    }
}