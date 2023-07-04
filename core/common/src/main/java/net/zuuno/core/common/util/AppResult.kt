package net.zuuno.core.common.util

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed class AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>()
    data class Error(val exception: Throwable) : AppResult<Nothing>()
    object Loading : AppResult<Nothing>()
}

fun <T> Flow<T>.asResult(): Flow<AppResult<T>> {
    return this
        .map<T, AppResult<T>> {
            AppResult.Success(it)
        }
        .onStart { emit(AppResult.Loading) }
        .catch { emit(AppResult.Error(it)) }
}

@Suppress("TooGenericExceptionCaught")
inline fun <R> resultOf(block: () -> R): AppResult<R> {
    return try {
        AppResult.Success(block())
    } catch (t: TimeoutCancellationException) {
        AppResult.Error(t)
    } catch (c: CancellationException) {
        throw c
    } catch (e: Exception) {
        AppResult.Error(e)
    }
}
