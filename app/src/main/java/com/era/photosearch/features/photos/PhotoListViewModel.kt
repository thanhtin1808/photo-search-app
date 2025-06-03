package com.era.photosearch.features.photos

import androidx.lifecycle.viewModelScope
import com.era.photosearch.compose.uistate.viewmodel.UiStateViewModel
import com.era.photosearch.domain.models.photos.PhotoModel
import com.era.photosearch.domain.usecases.photos.GetPhotoListUseCase
import com.era.photosearch.domain.usecases.preferences.IsFirstRunUseCase
import com.era.photosearch.domain.usecases.preferences.SetFirstRunUseCase
import com.era.photosearch.domain.utils.orTrue
import com.era.photosearch.features.photos.models.PhotoListEvent
import com.era.photosearch.features.photos.models.PhotoListUiState
import com.era.photosearch.providers.dispatchers.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PhotoListViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getPhotoListUseCase: GetPhotoListUseCase,
    private val isFirstRunUseCase: IsFirstRunUseCase,
    private val setFirstRunUseCase: SetFirstRunUseCase,
) : UiStateViewModel<PhotoListUiState, PhotoListEvent>(PhotoListUiState()) {

    private var currentPage = 1
    private val pageSize = 10
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()


    init {
        observeQuery()
        checkFirstRun()
    }

    private fun checkFirstRun() {
        launchSafe {
            val isFirstRun = isFirstRunUseCase().firstOrNull().orTrue()
            if (isFirstRun) {
                setFirstRunUseCase(false)
                sendEvent(PhotoListEvent.FirstRun)
            }
        }
    }


    fun refresh() {
        viewModelScope.launch(dispatcherProvider.io) {
            _isRefreshing.value = true
            searchPhotos(query.value, loadMore = false, fromRefresh = true)
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        viewModelScope.launch(dispatcherProvider.io) {
            query.debounce(600).distinctUntilChanged().collectLatest { queryText ->
                if (queryText.isBlank()) {
                    updateUiState { copy(photos = emptyList<PhotoModel>().toPersistentList()) }
                } else {
                    searchPhotos(queryText)
                }
            }
        }
    }


    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    private fun searchPhotos(query: String, loadMore: Boolean = false, fromRefresh: Boolean = false) {
        launchSafe(
            context = dispatcherProvider.io,
            hasLoading = !(loadMore || fromRefresh),
            onError = ::showError,
        ) {
            if (query.isBlank()) return@launchSafe
            if (!loadMore || query != this.query.value) {
                currentPage = 1
            }

            val photoList = getPhotoListUseCase(query, currentPage, pageSize)
            updateUiState {
                val newPhotos = if (loadMore && query == _query.value) {
                    (photos + photoList).distinctBy { it.id }.toPersistentList()
                } else {
                    photoList.toPersistentList()
                }

                copy(photos = newPhotos)
            }

            if (photoList.size == pageSize) {
                currentPage++
            }
            _isRefreshing.value = false
            if (fromRefresh){
                sendEvent(PhotoListEvent.RefreshSuccess)
            }
        }
    }


    fun loadMore() {
        if (isLastPage()) return
        searchPhotos(_query.value, loadMore = true)
    }

    private fun isLastPage(): Boolean {
        return uiState.value.photos.size % pageSize != 0
    }

    fun openUserDetail(user: PhotoModel) {
//        sendEvent(UserListEvent.OpenUserDetail(user.name))
    }

    fun onBackPress() {
        sendEvent(PhotoListEvent.onBackPress)
    }

    fun openUserLandingPage(user: PhotoModel) {
//        sendEvent(UserListEvent.OpenUserLandingPage(user.landingPageUrl))
    }
}