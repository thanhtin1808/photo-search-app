package com.era.photosearch.features.userdetail.models

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
internal data class PhotoDetailDestination(val photoSrc: String)