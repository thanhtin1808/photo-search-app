package com.era.photosearch.data.remote.models.responses

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("photos")
    val photos: T? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null,
    @SerializedName("next_page")
    val nextPage: String? = null,
)