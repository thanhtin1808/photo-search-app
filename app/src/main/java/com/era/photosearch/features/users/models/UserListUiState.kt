package com.era.photosearch.features.users.models

import com.era.photosearch.domain.models.users.UserModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

internal data class UserListUiState(
    val users: PersistentList<UserModel> = persistentListOf(),
)