package com.era.photosearch.features.users.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.era.photosearch.domain.models.users.UserModel
import com.era.photosearch.features.users.preview.previewUserList

@Composable
internal fun UserList(
    users: List<UserModel>,
    modifier: Modifier = Modifier,
    onUserLandingPageClick: (UserModel) -> Unit = {},
    onUserClick: (UserModel) -> Unit = {},
    onLoadMore: () -> Unit = {},
) {
    val listState = rememberLazyListState()
    /*Number of items from the end to trigger loading more*/
    val loadMoreThreshold = 4

    LazyColumn(
        state = listState,
        modifier = modifier
            .padding(top = 5.dp)
            .padding(horizontal = 16.dp)
    ) {
        items(users.size, key = { index -> users[index].id }) { index ->
            val user = users[index]
            Spacer(modifier = Modifier.height(3.dp))
            UserItem(
                userModel = user,
                onUserLandingPageClick = { onUserLandingPageClick(user) },
                onUserClick = { onUserClick(user) }
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }

    /*Detect when user scrolls near the end of the list*/
    LaunchedEffect(listState) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val lastVisibleItemIndex = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
            val totalItemCount = layoutInfo.totalItemsCount
            lastVisibleItemIndex to totalItemCount
        }.collect { (lastVisibleItemIndex, totalItemCount) ->
            if (totalItemCount > 0 && lastVisibleItemIndex >= totalItemCount - loadMoreThreshold) {
                onLoadMore()
            }
        }
    }
}

@Preview
@Composable
internal fun UserListPreview() {
    UserList(
        users = previewUserList
    )
}

