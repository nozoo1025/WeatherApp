package net.zuuno.component.weather.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import net.zuuno.component.weather.data.local.model.WeatherDataCached

@Dao
interface WeatherDataDao {

    @Query("SELECT * FROM WeatherDataCached LIMIT 1")
    fun getWeatherData(): Flow<WeatherDataCached?>

    @Upsert
    suspend fun saveWeatherData(weatherData: WeatherDataCached)
}