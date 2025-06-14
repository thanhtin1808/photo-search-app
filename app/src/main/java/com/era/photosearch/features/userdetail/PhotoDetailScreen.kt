package com.era.photosearch.features.userdetail

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.era.photosearch.features.photos.components.PhotoTopBar
import com.era.photosearch.R
import com.era.photosearch.compose.components.uistate.UiStateScreen
import com.era.photosearch.features.userdetail.components.PhotoDetailContent
import com.era.photosearch.features.userdetail.models.UserDetailEvent

@Composable
internal fun PhotoDetailScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = hiltViewModel(),
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
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(White),
        ) {
            PhotoTopBar(
                title = stringResource(R.string.photo_detail_top_bar_title),
                onBackClick = { navController.navigateUp() },
            )
            PhotoDetailContent(
                photoDetail = uiState.photoDetail,
                modifier = modifier
                    .padding(top = 5.dp)
                    .padding(horizontal = 16.dp),
            )
        }
    }
}

private fun handleEvent(
    event: UserDetailEvent,
    context: Context,
    navController: NavController,
) {
    when (event) {
        is UserDetailEvent.onBack -> {
            navController.navigateUp()
        }
    }
}

