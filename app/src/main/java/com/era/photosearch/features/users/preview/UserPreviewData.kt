package com.era.photosearch.features.users.preview

import com.era.photosearch.domain.models.users.UserModel


internal val previewUserList = MutableList(8) {
    index ->
    UserModel(
        id = index,
        avatar = "",
        name = "David $index",
        landingPageUrl = "https://www.linkedin.com/"
    )
}