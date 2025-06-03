package com.era.photosearch.features.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.era.photosearch.compose.uistate.viewmodel.UiStateViewModel
import com.era.photosearch.domain.models.photos.PhotoDetailModel
import com.era.photosearch.domain.models.photos.PhotoModel
import com.era.photosearch.features.userdetail.models.PhotoDetailDestination
import com.era.photosearch.features.userdetail.models.UserDetailEvent
import com.era.photosearch.features.userdetail.models.UserDetailUiState
import com.era.photosearch.providers.dispatchers.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class UserDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider,
) : UiStateViewModel<UserDetailUiState, UserDetailEvent>(UserDetailUiState()) {
    private val destination by lazy { savedStateHandle.toRoute<PhotoDetailDestination>() }

    init {
        val image = destination.photoSrc
        getPhotoDetail(image = image)
    }

    private fun getPhotoDetail(image: String) {
        launchSafe (
            context = dispatcherProvider.io,
            hasLoading = true,
            onError = ::showError,
        ) {
            updateUiState { copy(photoDetail = PhotoDetailModel(
                PhotoModel(originalSourceImage = image)
            )) }
        }
    }
}