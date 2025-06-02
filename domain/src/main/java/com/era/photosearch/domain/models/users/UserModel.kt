package com.era.photosearch.domain.models.users

data class UserModel(
    val id: Int = 0,
    val avatar: String = "",
    val name: String = "",
    val landingPageUrl: String = "",
)

data class UserDetailModel(
    val userModel: UserModel = UserModel(),
    val isVisibleLocation: Boolean = false,
    val location: String = "",
    val blogUrl: String = "",
    val followers: Int = 0,
    val following: Int = 0,
)