package com.era.photosearch.data.repositories.users.mappers

import com.android.photosearch.data.remote.models.responses.users.UserDetailResponse
import com.era.photosearch.data.remote.models.responses.photos.PhotoResponse
import com.era.photosearch.domain.models.photos.UserDetailModel
import com.era.photosearch.domain.models.photos.PhotoModel
import com.era.photosearch.domain.utils.orZero

internal fun PhotoResponse.toPhotoModel() = PhotoModel(
    id = id.orZero(),
    originalSourceImage = src?.original.orEmpty(),
)

internal fun UserDetailResponse.toUserDetailModel() = UserDetailModel(
    photoModel = PhotoModel(),
    isVisibleLocation = true,
    blogUrl = blog.orEmpty(),
    location = location.orEmpty(),
    followers = followers.orZero(),
    following = following.orZero(),
)

internal fun List<PhotoResponse>.toPhotoList() = map { response -> response.toPhotoModel() }