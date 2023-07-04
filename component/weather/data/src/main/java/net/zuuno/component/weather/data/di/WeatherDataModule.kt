package net.zuuno.component.weather.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.zuuno.component.weather.data.repository.WeatherDataRepositoryImpl
import net.zuuno.component.weather.domain.repository.WeatherDataRepository
import net.zuuno.component.weather.domain.usecase.GetWeatherDataUseCase
import net.zuuno.component.weather.domain.usecase.RefreshWeatherDataUseCase
import net.zuuno.component.weather.domain.usecase.getWeatherData
import net.zuuno.component.weather.domain.usecase.refreshWeatherData
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object WeatherDataModule {

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