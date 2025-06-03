package com.era.photosearch.features.photos.models

internal sealed class PhotoListEvent {

    data object FirstRun : PhotoListEvent()

    data object onBackPress : PhotoListEvent()

    data object RefreshSuccess : PhotoListEvent()

    data class OpenPhotoDetail(val userName: String) : PhotoListEvent()

    data class OpenPhotoLandingPage(val url: String) : PhotoListEvent()
}