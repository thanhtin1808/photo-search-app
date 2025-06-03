package com.era.photosearch.features.userdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.era.photosearch.domain.models.photos.PhotoDetailModel
import com.era.photosearch.features.photos.components.PhotoItem

@Composable
internal fun PhotoDetailContent(photoDetail: PhotoDetailModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        PhotoItem(
            modifier = Modifier.fillMaxSize(),
            photoModel = photoDetail.photoModel,
            onPhotoClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun TaskDetailContentPreview() {
    PhotoDetailContent(
        photoDetail = PhotoDetailModel()
    )
}