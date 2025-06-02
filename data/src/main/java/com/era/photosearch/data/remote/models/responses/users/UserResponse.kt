package com.android.photosearch.data.remote.models.responses.users

import com.google.gson.annotations.SerializedName

internal data class UserResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("html_url")
    val htmlUrl: String? = null,
)