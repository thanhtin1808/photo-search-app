package com.era.photosearch.data.repositories.users.mappers

import com.android.photosearch.data.remote.models.responses.users.UserDetailResponse
import com.era.photosearch.data.remote.models.responses.photos.PhotoResponse
import com.era.photosearch.domain.models.photos.PhotoDetailModel
import com.era.photosearch.domain.models.photos.PhotoModel
import com.era.photosearch.domain.utils.orFalse
import com.era.photosearch.domain.utils.orZero

internal fun PhotoResponse.toPhotoModel() = PhotoModel(
    id = id.orZero(),
    originalSourceImage = src?.original.orEmpty(),
)

internal fun PhotoResponse.toUserDetailModel() = PhotoDetailModel(
    photoModel = PhotoModel(),
    photographer = photographer.orEmpty(),
    like = liked.orFalse()
)

internal fun List<PhotoResponse>.toPhotoList() = map { response -> response.toPhotoModel() }