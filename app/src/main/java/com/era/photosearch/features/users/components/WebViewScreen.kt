package com.era.photosearch.features.users.components

import android.annotation.SuppressLint
import android.content.Context
import android.webkit.WebView
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.era.photosearch.features.users.WebViewModel
import com.era.photosearch.compose.components.uistate.UiStateScreen
import com.era.photosearch.features.users.models.WebViewEvent

@SuppressLint("SetJavaScriptEnabled")
@Composable
internal fun WebViewScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: WebViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    UiStateScreen(
        viewModel = viewModel,
        onEvent = { event ->
            handleEvent(
                event = event,
                context = context,
                navController = navController,
            )
        },
    ) { uiState ->
        AndroidView(modifier = modifier
            .fillMaxSize()
            .padding(top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()),
            factory = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                loadUrl(uiState.url)
            }
        })
    }
}

private fun handleEvent(
    event: WebViewEvent,
    context: Context,
    navController: NavController,
) {
    when (event) {
        is WebViewEvent.onBack -> {
            navController.navigateUp()
        }
    }
}