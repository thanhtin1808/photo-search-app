package com.era.photosearch.features.photos.models

internal sealed class WebViewEvent {
    data object onBack : WebViewEvent()
}