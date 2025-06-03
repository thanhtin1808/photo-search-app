package com.era.photosearch.features.userdetail.models

import com.era.photosearch.domain.models.photos.UserDetailModel

internal data class UserDetailUiState(
    val userDetail: UserDetailModel = UserDetailModel(),
)