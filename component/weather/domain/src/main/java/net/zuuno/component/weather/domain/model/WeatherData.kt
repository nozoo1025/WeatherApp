package net.zuuno.component.weather.domain.model

/**
 * 天気情報
 *
 * @param id ID
 * @param location 地域
 * @param weather 天気
 * @param temperature 気温
 */
data class WeatherData(
    val id: String,
    val location: String,
    val weather: Weather,
    val temperature: Double,
)
