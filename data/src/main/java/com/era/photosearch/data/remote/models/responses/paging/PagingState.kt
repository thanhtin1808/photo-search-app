package com.era.photosearch.data.remote.models.responses.paging

import com.era.photosearch.data.remote.models.responses.photos.PhotoResponse


internal data class PagingState(
    val currentPage: Int = 1,
    val isLoading: Boolean = false,
    val users: List<PhotoResponse> = emptyList(),
)