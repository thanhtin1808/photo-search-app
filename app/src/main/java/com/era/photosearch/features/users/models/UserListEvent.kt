package com.era.photosearch.features.users.models

internal sealed class UserListEvent {

    data object FirstRun : UserListEvent()

    data object onBackPress : UserListEvent()

    data class OpenUserDetail(val userName: String) : UserListEvent()

    data class OpenUserLandingPage(val url: String) : UserListEvent()
}