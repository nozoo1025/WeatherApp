package net.zuuno.feature.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.zuuno.feature.weather.viewmodel.WeatherIntent
import net.zuuno.feature.weather.viewmodel.WeatherUiState
import net.zuuno.feature.weather.viewmodel.WeatherViewModel

@Composable
internal fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    WeatherContent(
        uiState = uiState.value,
        onIntent = viewModel::acceptIntent,
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
    )
}

@Composable
private fun WeatherContent(
    uiState: WeatherUiState,
    onIntent: (WeatherIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.weatherData != null) {
        WeatherAvailableContent(
            uiState = uiState,
            onRefresh = { onIntent(WeatherIntent.RefreshWeatherData) },
            modifier = modifier,
        )
    } else {
        WeatherNotAvailableContent(
            uiState = uiState,
            modifier = modifier,
        )
    }
}

@Composable
private fun WeatherAvailableContent(
    uiState: WeatherUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(text = "天気情報")
        Text(text = uiState.weatherData?.location ?: "")
        Text(text = uiState.weatherData?.weather ?: "")
        Button(onClick = onRefresh) {
            Text(text = "更新")
        }
    }
}

@Composable
private fun WeatherNotAvailableContent(
    uiState: WeatherUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator()
            }

            uiState.isError -> {
                Text(text = "Error!")
            }
        }
    }
}

