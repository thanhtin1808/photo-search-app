package com.android.photosearch.data.remote.models.responses.paging

import com.android.photosearch.data.remote.models.responses.users.UserResponse


internal data class PagingState(
    val currentPage: Int = 1,
    val isLoading: Boolean = false,
    val users: List<UserResponse> = emptyList(),
)