package net.zuuno.component.weather.domain.usecase

import net.zuuno.component.weather.domain.repository.WeatherDataRepository
import net.zuuno.core.common.util.resultOf
import net.zuuno.core.common.util.AppResult

fun interface RefreshWeatherDataUseCase : suspend () -> AppResult<Unit>

suspend fun refreshWeatherData(
    weatherDataRepository: WeatherDataRepository,
): AppResult<Unit> = resultOf {
    weatherDataRepository.refreshWeatherData()
}