package com.era.photosearch.features.photos.models

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
internal data class WebViewDestination(val url: String)