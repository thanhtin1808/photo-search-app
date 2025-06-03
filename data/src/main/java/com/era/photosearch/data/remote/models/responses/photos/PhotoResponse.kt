package com.era.photosearch.data.remote.models.responses.photos

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("width") val width: Int? = null,
    @SerializedName("height") val height: Int? = null,
    @SerializedName("url") val url: String? = null,
    @SerializedName("photographer") val photographer: String? = null,
    @SerializedName("photographer_url") val photographerUrl: String? = null,
    @SerializedName("photographer_id") val photographerId: Int? = null,
    @SerializedName("avg_color") val avgColor: String? = null,
    @SerializedName("src") val src: PhotoSource? = null,
    @SerializedName("liked") val liked: Boolean? = null,
    @SerializedName("alt") val alt: String? = null
)

data class PhotoSource(
    @SerializedName("original") val original: String? = null,
    @SerializedName("large2x") val large2x: String? = null,
    @SerializedName("large") val large: String? = null,
    @SerializedName("medium") val medium: String? = null,
    @SerializedName("small") val small: String? = null,
    @SerializedName("portrait") val portrait: String? = null,
    @SerializedName("landscape") val landscape: String? = null,
    @SerializedName("tiny") val tiny: String? = null
)
