package com.era.photosearch.features.userdetail.models

internal sealed class UserDetailEvent {
    data object onBack : UserDetailEvent()
}