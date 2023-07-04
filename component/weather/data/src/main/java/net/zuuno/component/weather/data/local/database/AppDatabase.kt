package net.zuuno.component.weather.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import net.zuuno.component.weather.data.local.dao.WeatherDataDao
import net.zuuno.component.weather.data.local.model.WeatherDataCached

@Database(
    entities = [WeatherDataCached::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
}