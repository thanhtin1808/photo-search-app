package com.era.photosearch.features.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.era.photosearch.providers.dispatchers.DispatcherProvider
import com.era.photosearch.compose.uistate.viewmodel.UiStateViewModel
import com.era.photosearch.domain.usecases.photos.GetUserDetailUseCase
import com.era.photosearch.features.userdetail.models.UserDetailDestination
import com.era.photosearch.features.userdetail.models.UserDetailEvent
import com.era.photosearch.features.userdetail.models.UserDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider,
    private val getUserDetailUseCase: GetUserDetailUseCase,
) : UiStateViewModel<UserDetailUiState, UserDetailEvent>(UserDetailUiState()) {
    private val destination by lazy { savedStateHandle.toRoute<UserDetailDestination>() }

    init {
        val userName = destination.userName
        getUserList(userName = userName)
    }

    private fun getUserList(userName: String) {
        launchSafe (
            context = dispatcherProvider.io,
            hasLoading = true,
            onError = ::showError,
        ) {
            val userDetail = getUserDetailUseCase(userName = userName)
            updateUiState { copy(userDetail = userDetail) }
        }
    }
}