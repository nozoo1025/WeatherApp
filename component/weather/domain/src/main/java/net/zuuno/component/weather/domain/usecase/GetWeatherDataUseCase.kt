package net.zuuno.component.weather.domain.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retryWhen
import net.zuuno.component.weather.domain.model.WeatherData
import net.zuuno.component.weather.domain.repository.WeatherDataRepository
import net.zuuno.core.common.util.AppResult
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L

fun interface GetWeatherDataUseCase : () -> Flow<AppResult<WeatherData?>>

fun getWeatherData(
    weatherDataRepository: WeatherDataRepository,
): Flow<AppResult<WeatherData?>> {
    return weatherDataRepository.getWeatherData()
        .map<WeatherData?, AppResult<WeatherData?>> { AppResult.Success(it) }
        .retryWhen { cause, _ ->
            if (cause is IOException) {
                emit(AppResult.Error(cause))

                delay(RETRY_TIME_IN_MILLIS)

                true
            } else {
                false
            }
        }
        .catch {
            emit(AppResult.Error(it))
        }
}