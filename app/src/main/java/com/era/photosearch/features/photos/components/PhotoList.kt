package com.era.photosearch.features.photos.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.era.photosearch.domain.models.photos.PhotoModel
import com.era.photosearch.features.photos.preview.previewPhotoList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
internal fun PhotoList(
    photos: List<PhotoModel>,
    modifier: Modifier = Modifier,
    onPhotoClick: (PhotoModel) -> Unit = {},
    onLoadMore: () -> Unit = {},
) {
    val listState =
        rememberLazyStaggeredGridState()/*Number of items from the end to trigger loading more*/
    val loadMoreThreshold = 3

    Box(modifier = modifier.padding(bottom = 20.dp).fillMaxSize()) {
        LazyVerticalStaggeredGrid(
            state = listState,
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
            modifier = modifier
                .padding(top = 5.dp)
                .padding(horizontal = 16.dp)
        ) {
            if (photos.isNotEmpty()) {
                items(photos.size, key = { index -> photos[index].id }) { index ->
                    val photo = photos[index]
                    Spacer(modifier = Modifier.height(3.dp))
                    PhotoItem(
                        photoModel = photo, onPhotoClick = { onPhotoClick(photo) })
                    Spacer(modifier = Modifier.height(5.dp))
                }
            }
        }
    }

    /*Detect when user scrolls near the end of the list*/
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }.map { layoutInfo ->
            val visibleItems = layoutInfo.visibleItemsInfo
            val lastVisibleIndex = visibleItems.maxOfOrNull { it.index } ?: -1
            val totalItemsCount = layoutInfo.totalItemsCount
            lastVisibleIndex to totalItemsCount
        }.distinctUntilChanged().collect { (lastVisibleIndex, totalItemsCount) ->
            if (totalItemsCount > 0 && lastVisibleIndex >= totalItemsCount - loadMoreThreshold) {
                onLoadMore()
            }
        }
    }

}

@Preview
@Composable
internal fun PhotoListPreview() {
    PhotoList(
        photos = previewPhotoList
    )
}

