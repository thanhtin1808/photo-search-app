package com.era.photosearch.features.users.models

internal sealed class WebViewEvent {
    data object onBack : WebViewEvent()
}