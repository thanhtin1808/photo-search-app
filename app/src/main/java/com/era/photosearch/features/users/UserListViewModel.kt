package com.era.photosearch.features.users

import com.era.photosearch.providers.dispatchers.DispatcherProvider
import com.era.photosearch.compose.uistate.viewmodel.UiStateViewModel
import com.era.photosearch.domain.models.users.UserModel
import com.era.photosearch.domain.usecases.preferences.IsFirstRunUseCase
import com.era.photosearch.domain.usecases.preferences.SetFirstRunUseCase
import com.era.photosearch.domain.usecases.users.GetUserListUseCase
import com.era.photosearch.domain.utils.orTrue
import com.era.photosearch.features.users.models.UserListEvent
import com.era.photosearch.features.users.models.UserListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
internal class UserListViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getUserListUseCase: GetUserListUseCase,
    private val isFirstRunUseCase: IsFirstRunUseCase,
    private val setFirstRunUseCase: SetFirstRunUseCase,
) : UiStateViewModel<UserListUiState, UserListEvent>(UserListUiState()) {

    private var currentPage = 1
    private val pageSize = 20

    init {
        getUserList()
        checkFirstRun()
    }

    private fun checkFirstRun() {
        launchSafe {
            val isFirstRun = isFirstRunUseCase().firstOrNull().orTrue()
            if (isFirstRun) {
                setFirstRunUseCase(false)
                sendEvent(UserListEvent.FirstRun)
            }
        }
    }

    private fun getUserList(loadMore: Boolean = false) {
        launchSafe(
            context = dispatcherProvider.io,
            hasLoading = !loadMore,
            onError = ::showError,
        ) {
            if (!loadMore) {
                currentPage = 1
            }
            val userList = getUserListUseCase(currentPage, pageSize)
            updateUiState {
                copy(
                    users = if (loadMore) {
                        users.addAll(userList).toPersistentList()
                    } else {
                        userList.toPersistentList()
                    }
                )
            }
            if (userList.size == pageSize) {
                currentPage++
            }
        }
    }

    fun loadMore() {
        if (isLastPage()) return
        getUserList(loadMore = true)
    }

    private fun isLastPage(): Boolean {
        return uiState.value.users.size % pageSize != 0
    }

    fun openUserDetail(user: UserModel) {
        sendEvent(UserListEvent.OpenUserDetail(user.name))
    }

    fun onBackPress() {
        sendEvent(UserListEvent.onBackPress)
    }

    fun openUserLandingPage(user: UserModel) {
        sendEvent(UserListEvent.OpenUserLandingPage(user.landingPageUrl))
    }
}