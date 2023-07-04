package net.zuuno.feature.weather.viewmodel

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import net.zuuno.component.weather.domain.usecase.GetWeatherDataUseCase
import net.zuuno.component.weather.domain.usecase.RefreshWeatherDataUseCase
import net.zuuno.core.presentation.util.BaseViewModel
import javax.inject.Inject
import net.zuuno.core.common.util.AppResult
import net.zuuno.feature.weather.mapper.toPresentationModel
import net.zuuno.feature.weather.viewmodel.WeatherUiState.PartialState.Fetched
import net.zuuno.feature.weather.viewmodel.WeatherUiState.PartialState.Error
import net.zuuno.feature.weather.viewmodel.WeatherUiState.PartialState.Loading

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val refreshWeatherDataUseCase: RefreshWeatherDataUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel<WeatherUiState, WeatherUiState.PartialState, WeatherEvent, WeatherIntent>(
    savedStateHandle = savedStateHandle,
    initialState = WeatherUiState(),
) {

    init {
        observeContinuousChanges(getWeatherData())
    }

    override fun mapIntents(intent: WeatherIntent): Flow<WeatherUiState.PartialState> =
        when (intent) {
            is WeatherIntent.GetWeatherData -> getWeatherData()
            is WeatherIntent.RefreshWeatherData -> refreshWeatherData()
        }

    override fun reduceUiState(
        previousState: WeatherUiState,
        partialState: WeatherUiState.PartialState
    ): WeatherUiState = when (partialState) {
        is Loading -> previousState.copy(
            isLoading = true,
            isError = false,
        )

        is Fetched -> previousState.copy(
            isLoading = false,
            weatherData = partialState.weatherData,
            isError = false,
        )

        is Error -> previousState.copy(
            isLoading = false,
            isError = true,
        )
    }

    private fun getWeatherData(): Flow<WeatherUiState.PartialState> =
        getWeatherDataUseCase()
            .map { result ->
                when (result) {
                    is AppResult.Success -> Fetched(result.data?.toPresentationModel())
                    is AppResult.Error -> Error(result.exception)
                    is AppResult.Loading -> Loading
                }
            }
            .onStart {
                emit(Loading)
            }

    private fun refreshWeatherData(): Flow<WeatherUiState.PartialState> = flow {
        refreshWeatherDataUseCase().let {
            if (it is AppResult.Error) {
                emit(Error(it.exception))
            }
        }
    }
}