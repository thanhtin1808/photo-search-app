package com.era.photosearch.compose.uistate.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.era.photosearch.compose.uistate.models.ErrorState
import com.era.photosearch.extenstions.viewmodel.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class UiStateViewModel<UiState, Event>(
    initialUiState: UiState,
    private val mutexUiState: Mutex = Mutex(),
    private val mutexError: Mutex = Mutex(),
    private val mutexLoading: Mutex = Mutex(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(initialUiState)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    private val _error = MutableStateFlow(ErrorState())
    val error: StateFlow<ErrorState>
        get() = _error.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _isLoading.asStateFlow()

    private val _singleEvent = Channel<Event>(Channel.BUFFERED)
    val singleEvent: Flow<Event>
        get() = _singleEvent.receiveAsFlow()

    open fun getUiState(): UiState = _uiState.value

    open fun updateUiState(update: UiState.() -> UiState) {
        launch { mutexUiState.withLock { _uiState.emit(_uiState.value.update()) } }
    }

    open fun showError(throwable: Throwable) {
        launch { mutexError.withLock { _error.emit(ErrorState(throwable)) } }
    }

    open fun hideError() {
        launch { mutexError.withLock { _error.emit(ErrorState()) } }
    }

    open fun showLoading() {
        launch { mutexLoading.withLock { _isLoading.emit(true) } }
    }

    open fun hideLoading() {
        launch { mutexLoading.withLock { _isLoading.emit(false) } }
    }

    open fun sendEvent(event: Event) {
        launch { _singleEvent.send(event) }
    }

    fun launchSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        onError: (Throwable) -> Unit = {},
        hasLoading: Boolean = false,
        block: suspend () -> Unit,
    ): Job {
        if (hasLoading) showLoading()
        return launch(context) {
            try {
                block()
            } catch (e: Throwable) {
                Timber.e(e)
                onError(e)
            }
            if (hasLoading) hideLoading()
        }
    }

    fun <T> Flow<T>.collectSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        hasLoading: Boolean = false,
        onError: (Throwable) -> Unit = {},
        block: suspend (T) -> Unit,
    ): Job = flowOn(context)
        .onStart { if (hasLoading) showLoading() }
        .catch { e ->
            Timber.e(e)
            onError(e)
            if (hasLoading) hideLoading()
        }
        .onEach {
            block(it)
            if (hasLoading) hideLoading()
        }
        .launchIn(viewModelScope)
}