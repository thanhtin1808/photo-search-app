package com.era.photosearch.features.userdetail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.era.photosearch.domain.models.photos.UserDetailModel
import com.era.photosearch.features.photos.components.PhotoItem

@Composable
internal fun UserDetailContent(userDetail: UserDetailModel, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        PhotoItem(
            modifier = Modifier.height(130.dp),
            photoModel = userDetail.photoModel,
            onPhotoClick = {},
        )
        UserStatisticsItem(
            modifier = modifier,
            followerCount = userDetail.followers.toString(),
            followingCount = userDetail.following.toString()
        )
        BlogItem(blogUrl = userDetail.blogUrl, modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
internal fun TaskDetailContentPreview() {
    UserDetailContent(
        userDetail = UserDetailModel()
    )
}