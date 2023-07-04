package net.zuuno.core.presentation.util

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import timber.log.Timber

private const val SAVED_UI_STATE_KEY = "savedUiStateKey"

abstract class BaseViewModel<UI_STATE : Parcelable, PARTIAL_UI_STATE, EVENT, INTENT>(
    savedStateHandle: SavedStateHandle,   // UI_STATEを保存・復元するためのハンドラ
    initialState: UI_STATE,               // UI_STATEの初期状態
) : ViewModel() {
    // ユーザーからのINTENTを受け取るためのFlow
    private val intentFlow = MutableSharedFlow<INTENT>()
    // 継続的なUI状態変化を受け取るためのFlow
    private val continuousPartialStateFlow = MutableSharedFlow<PARTIAL_UI_STATE>()

    // INTENTと継続的なUI状態変化を受け取る準備が整ったことを示すフラグ
    private val intentFlowListenerStarted = CompletableDeferred<Unit>()
    private val continuousPartialStateFlowListenerStarted = CompletableDeferred<Unit>()

    // UIの状態を表すFlow。初期状態はinitialStateで、savedStateHandleから復元も可能
    val uiState = savedStateHandle.getStateFlow(SAVED_UI_STATE_KEY, initialState)

    // イベントを送信するためのChannel
    private val eventChannel = Channel<EVENT>(Channel.BUFFERED)
    // eventChannelからイベントを受け取るFlow
    val event = eventChannel.receiveAsFlow()

    // ViewModelの初期化時に実行される処理
    init {
        // UIの状態変化を監視し、新しい状態をsavedStateHandleに保存する
        viewModelScope.launch {
            merge(
                userIntents(),         // ユーザーからのINTENTをFlowとして取得
                continuousFlows(),     // 継続的なUI状態変化をFlowとして取得
            )
                .scan(uiState.value, ::reduceUiState)  // 新たなUI_STATEを生成
                .catch { Timber.e(it) }  // エラーが発生した場合はログに出力
                .collect {
                    // 新たなUI_STATEをsavedStateHandleに保存
                    savedStateHandle[SAVED_UI_STATE_KEY] = it
                }
        }
    }

    // ユーザーからのINTENTをPARTIAL_UI_STATEに変換するFlowを生成
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun userIntents(): Flow<PARTIAL_UI_STATE> =
        intentFlow
            .onStart { intentFlowListenerStarted.complete(Unit) }  // INTENT受信の準備が整ったことを通知
            .flatMapConcat(::mapIntents)  // 各INTENTをPARTIAL_UI_STATEにマッピング

    // 継続的なUI状態変化を取得するFlowを生成
    private fun continuousFlows(): Flow<PARTIAL_UI_STATE> =
        continuousPartialStateFlow
            .onStart { continuousPartialStateFlowListenerStarted.complete(Unit) }  // UI状態変化受信の準備が整ったことを通知

    // ユーザーからのINTENTを受け付けるメソッド
    fun acceptIntent(intent: INTENT) {
        viewModelScope.launch {
            intentFlowListenerStarted.await()  // INTENT受信の準備が整うまで待機
            intentFlow.emit(intent)  // INTENTをFlowに送信
        }
    }

    // 継続的なUI状態変化を監視するメソッド
    protected fun observeContinuousChanges(changesFlow: Flow<PARTIAL_UI_STATE>) {
        viewModelScope.launch {
            continuousPartialStateFlowListenerStarted.await()  // UI状態変化受信の準備が整うまで待機
            continuousPartialStateFlow.emitAll(changesFlow)  // UI状態変化をFlowに送信
        }
    }

    // イベントを発行するメソッド
    protected fun publishEvent(event: EVENT) {
        viewModelScope.launch {
            eventChannel.send(event)  // イベントをChannelに送信
        }
    }

    // ユーザーからのINTENTをPARTIAL_UI_STATEにマッピングするメソッド（具体的な実装はサブクラスに任される）
    protected abstract fun mapIntents(intent: INTENT): Flow<PARTIAL_UI_STATE>

    // 前のUI_STATEとPARTIAL_UI_STATEから新たなUI_STATEを生成するメソッド（具体的な実装はサブクラスに任される）
    protected abstract fun reduceUiState(
        previousState: UI_STATE,
        partialState: PARTIAL_UI_STATE,
    ): UI_STATE
}
