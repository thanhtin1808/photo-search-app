package com.android.photosearch.features.userdetail

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import com.era.photosearch.compose.uistate.models.ErrorState
import com.era.photosearch.domain.models.errors.NoConnectionException
import com.era.photosearch.domain.models.users.UserDetailModel
import com.era.photosearch.domain.models.users.UserModel
import com.era.photosearch.domain.usecases.users.GetUserDetailUseCase
import com.era.photosearch.features.userdetail.models.UserDetailDestination
import com.era.photosearch.features.userdetail.models.UserDetailUiState
import com.era.photosearch.providers.dispatchers.DispatcherProvider
import com.android.photosearch.utils.mockkSavedStateHandle
import com.android.photosearch.utils.unmockkSavedStateHandle
import com.era.photosearch.features.userdetail.UserDetailViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class UserDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private val dispatcherProvider: DispatcherProvider = mockk()
    private val getUserDetailUseCase: GetUserDetailUseCase = mockk()

    private lateinit var userDetailViewModel: UserDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkSavedStateHandle()
        every { dispatcherProvider.io } returns testDispatcher
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkSavedStateHandle()
    }

    @Test
    fun `getUserDetail is success`() = runTest {
        // Given
        val userDetail = UserDetailModel(userModel = UserModel(name = "UserDetail 1"))
        coEvery { getUserDetailUseCase("UserDetail 1") } returns userDetail
        every {
            savedStateHandle.toRoute<UserDetailDestination>()
        } returns UserDetailDestination(userName = "UserDetail 1")

        // When
        userDetailViewModel = createUserDetailDetailViewModel()
        advanceUntilIdle()

        // Then
        userDetailViewModel.uiState.test {
            expectMostRecentItem() shouldBe UserDetailUiState(userDetail = userDetail)
        }
    }

    @Test
    fun `getUserDetail is fail`() = runTest {
        // Given
        val error = NoConnectionException()
        coEvery { getUserDetailUseCase("user1") } throws error
        coEvery {
            savedStateHandle.toRoute<UserDetailDestination>()
        } returns UserDetailDestination(userName = "user1")

        // When
        userDetailViewModel = createUserDetailDetailViewModel()
        advanceUntilIdle()

        // Then
        userDetailViewModel.error.test {
            expectMostRecentItem() shouldBe ErrorState(error)
        }
    }

    private fun createUserDetailDetailViewModel() = UserDetailViewModel(
        savedStateHandle = savedStateHandle,
        dispatcherProvider = dispatcherProvider,
        getUserDetailUseCase = getUserDetailUseCase,
    )
}