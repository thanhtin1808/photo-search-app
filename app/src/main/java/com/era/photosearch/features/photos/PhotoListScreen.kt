package com.era.photosearch.features.photos

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.era.photosearch.R
import com.era.photosearch.compose.components.uistate.UiStateScreen
import com.era.photosearch.features.photos.components.EmptyPhotoSearch
import com.era.photosearch.features.photos.components.PhotoList
import com.era.photosearch.features.photos.components.PhotoTopBar
import com.era.photosearch.features.photos.models.PhotoListEvent
import com.era.photosearch.features.photos.models.WebViewDestination
import com.era.photosearch.features.userdetail.models.PhotoDetailDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PhotoListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: PhotoListViewModel = hiltViewModel(),
) {
    val refreshing = viewModel.isRefreshing.collectAsState()
    val context = LocalContext.current
    BackHandler {
        val activity = (context as? Activity)
        activity?.finish()
    }
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
        val query = viewModel.query.collectAsState() // observe current query
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(White),
        ) {
            PhotoTopBar(
                title = stringResource(R.string.photo_list_home_top_bar_title),
                onBackClick = viewModel::onBackPress,
            )
            OutlinedTextField(
                value = query.value,
                onValueChange = { newText ->
                    viewModel.onQueryChanged(newText)
                },
                label = { Text(stringResource(R.string.txt_photo_search_label)) },
                placeholder = { Text(stringResource(R.string.txt_photo_search_hint)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            )
            if (query.value.trim().isNotBlank()) {
                PullToRefreshBox(
                    isRefreshing = refreshing.value,
                    onRefresh = { viewModel.refresh() }, // Pull-to-refresh action
                ) {
                    PhotoList(
                        photos = uiState.photos,
                        onPhotoClick = viewModel::openPhotoDetail,
                        onLoadMore = { viewModel.loadMore() },
                    )
                }
            } else {
                EmptyPhotoSearch(modifier = modifier.fillMaxSize())
            }

        }
    }
}

private fun handleEvent(
    event: PhotoListEvent,
    context: Context,
    navController: NavController,
) {
    when (event) {
        is PhotoListEvent.FirstRun -> {
            Toast.makeText(context, R.string.first_run_message, Toast.LENGTH_SHORT).show()
        }

        is PhotoListEvent.onBackPress -> {
            val activity = (context as? Activity)
            activity?.finish()
        }

        is PhotoListEvent.RefreshSuccess -> {
            Toast.makeText(context,
                context.getString(R.string.txt_photo_list_has_refreshed), Toast.LENGTH_SHORT).show()
        }

        is PhotoListEvent.OpenPhotoDetail -> {
            navController.navigate(PhotoDetailDestination(photoSrc = event.url))
        }

        is PhotoListEvent.OpenPhotoLandingPage -> {
            navController.navigate(WebViewDestination(url = event.url))
        }
    }
}

