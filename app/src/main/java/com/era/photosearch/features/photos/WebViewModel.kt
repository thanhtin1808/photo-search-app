package com.era.photosearch.features.photos

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.era.photosearch.providers.dispatchers.DispatcherProvider
import com.era.photosearch.compose.uistate.viewmodel.UiStateViewModel
import com.era.photosearch.features.photos.models.WebViewDestination
import com.era.photosearch.features.photos.models.WebViewEvent
import com.era.photosearch.features.photos.models.WebViewUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class WebViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider,
) : UiStateViewModel<WebViewUiState, WebViewEvent>(WebViewUiState()) {
    private val destination by lazy { savedStateHandle.toRoute<WebViewDestination>() }

    init {
        val url = destination.url
        updateUiState { copy(url = url) }
    }

}