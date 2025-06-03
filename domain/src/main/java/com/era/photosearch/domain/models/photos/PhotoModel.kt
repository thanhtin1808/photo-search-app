package com.era.photosearch.domain.models.photos

data class PhotoModel(
    val id: Long = 0,
    val originalSourceImage: String = "",
)

data class PhotoDetailModel(
    val photoModel: PhotoModel = PhotoModel(),
    val photographer: String = "",
    val like: Boolean = false,
)