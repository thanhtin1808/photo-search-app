package com.era.photosearch.features.userdetail.models

import com.era.photosearch.domain.models.photos.PhotoDetailModel

internal data class UserDetailUiState(
    val photoDetail: PhotoDetailModel = PhotoDetailModel(),
)