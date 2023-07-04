package net.zuuno.component.weather.data.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import net.zuuno.component.weather.data.local.dao.WeatherDataDao
import net.zuuno.component.weather.data.local.database.AppDatabase
import net.zuuno.component.weather.data.remote.api.WeatherApi
import net.zuuno.component.weather.data.repository.WeatherDataRepositoryImpl
import net.zuuno.component.weather.domain.repository.WeatherDataRepository
import net.zuuno.component.weather.domain.usecase.GetWeatherDataUseCase
import net.zuuno.component.weather.domain.usecase.RefreshWeatherDataUseCase
import net.zuuno.component.weather.domain.usecase.getWeatherData
import net.zuuno.component.weather.domain.usecase.refreshWeatherData
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object WeatherDataModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database_name",
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDataDao(database: AppDatabase): WeatherDataDao {
        return database.weatherDataDao()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()

        return Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi(
        retrofit: Retrofit,
    ): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGetWeatherDataUseCase(
        weatherDataRepository: WeatherDataRepository,
    ): GetWeatherDataUseCase {
        return GetWeatherDataUseCase {
            getWeatherData(weatherDataRepository)
        }
    }

    @Provides
    @Singleton
    fun provideRefreshWeatherDataUseCase(
        weatherDataRepository: WeatherDataRepository,
    ): RefreshWeatherDataUseCase {
        return RefreshWeatherDataUseCase {
            refreshWeatherData(weatherDataRepository)
        }
    }
}


@Module
@InstallIn(SingletonComponent::class)
internal interface BindsModule {

    @Binds
    fun bindWeatherDataRepository(impl: WeatherDataRepositoryImpl): WeatherDataRepository
}