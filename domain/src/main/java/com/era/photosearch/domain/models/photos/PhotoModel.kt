package com.era.photosearch.domain.models.photos

data class PhotoModel(
    val id: Long = 0,
    val originalSourceImage: String = "",
)

data class UserDetailModel(
    val photoModel: PhotoModel = PhotoModel(),
    val isVisibleLocation: Boolean = false,
    val location: String = "",
    val blogUrl: String = "",
    val followers: Int = 0,
    val following: Int = 0,
)