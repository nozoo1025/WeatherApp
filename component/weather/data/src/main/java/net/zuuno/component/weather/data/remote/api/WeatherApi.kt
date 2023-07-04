package net.zuuno.component.weather.data.remote.api

import net.zuuno.component.weather.data.remote.model.WeatherResponse
import retrofit2.http.GET

interface WeatherApi {

    @GET("v1/forecast?latitude=35.6762&longitude=139.6503&daily=weathercode&current_weather=true&timezone=Asia%2FTokyo&forecast_days=1")
    suspend fun getWeather(): WeatherResponse
}