package com.era.photosearch.features.photos.models

import com.era.photosearch.domain.models.photos.PhotoModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal data class PhotoListUiState(
    val photos: PersistentList<PhotoModel> = persistentListOf(),
)