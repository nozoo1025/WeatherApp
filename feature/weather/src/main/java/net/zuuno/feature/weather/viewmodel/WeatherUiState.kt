package net.zuuno.feature.weather.viewmodel

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.parcelize.Parcelize
import net.zuuno.feature.weather.model.WeatherDataDisplayable

@Immutable
@Parcelize
data class WeatherUiState(
    val isLoading: Boolean = false,
    val weatherData: WeatherDataDisplayable? = null,
    val isError: Boolean = false,
) : Parcelable {

    sealed class PartialState {
        object Loading : PartialState()

        data class Fetched(val list: WeatherDataDisplayable) : PartialState()

        data class Error(val throwable: Throwable) : PartialState()
    }
}