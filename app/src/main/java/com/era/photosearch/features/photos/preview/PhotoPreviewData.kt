package com.era.photosearch.features.photos.preview

import com.era.photosearch.domain.models.photos.PhotoModel


internal val previewPhotoList = MutableList(8) {
    index ->
    PhotoModel(
        id = index.toLong(),
        originalSourceImage = "https://images.pexels.com/photos/32370562/pexels-photo-32370562.jpeg",
    )
}