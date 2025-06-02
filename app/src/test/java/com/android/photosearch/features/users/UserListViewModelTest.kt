package com.android.photosearch.features.users

import app.cash.turbine.test
import com.era.photosearch.compose.uistate.models.ErrorState
import com.era.photosearch.domain.models.errors.NoConnectionException
import com.era.photosearch.domain.models.users.UserModel
import com.era.photosearch.domain.usecases.preferences.IsFirstRunUseCase
import com.era.photosearch.domain.usecases.preferences.SetFirstRunUseCase
import com.era.photosearch.domain.usecases.users.GetUserListUseCase
import com.era.photosearch.features.users.models.UserListEvent
import com.era.photosearch.features.users.models.UserListUiState
import com.era.photosearch.providers.dispatchers.DispatcherProvider
import com.era.photosearch.features.users.UserListViewModel
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class UserListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val dispatcherProvider: DispatcherProvider = mockk()
    private val getUserListUseCase: GetUserListUseCase = mockk()
    private val isFirstRunUseCase: IsFirstRunUseCase = mockk()
    private val setFirstRunUseCase: SetFirstRunUseCase = mockk()

    private lateinit var userListViewModel: UserListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { dispatcherProvider.io } returns testDispatcher
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserModelList is success`() = runTest {
        // Given
        val users = giveUserModelList()

        // When
        userListViewModel = createUserListViewModel()
        advanceUntilIdle()

        // Then
        userListViewModel.uiState.test {
            expectMostRecentItem() shouldBe UserListUiState(users = users.toPersistentList())
        }
    }

    @Test
    fun `getUserModelList is fail`() = runTest {
        val error = NoConnectionException()
        coEvery { getUserListUseCase(page = 1, pageSize = 20) } throws error

        // When
        userListViewModel = createUserListViewModel()
        advanceUntilIdle()

        // Then
        userListViewModel.error.test {
            expectMostRecentItem() shouldBe ErrorState(error)
        }
    }

    @Test
    fun `isFirstRun is true`() = runTest {
        // Given
        giveUserModelList()

        val expected = true
        every { isFirstRunUseCase() } returns flowOf(expected)
        coEvery { setFirstRunUseCase(false) } returns Unit

        // When
        userListViewModel = createUserListViewModel()
        advanceUntilIdle()

        // Then
        userListViewModel.singleEvent.test {
            expectMostRecentItem() shouldBe UserListEvent.FirstRun
        }
    }

    @Test
    fun `isFirstRun is false`() = runTest {
        // Given
        giveUserModelList()

        val expected = false
        every { isFirstRunUseCase() } returns flowOf(expected)

        // When
        userListViewModel = createUserListViewModel()
        advanceUntilIdle()

        // Then
        userListViewModel.singleEvent.test {
            expectNoEvents()
        }
    }

    @Test
    fun `openUserModelDetail calls`() = runTest {
        // Given
        val userModels = giveUserModelList()

        // When
        userListViewModel = createUserListViewModel()
        advanceUntilIdle()

        userListViewModel.openUserDetail(userModels.first())
        advanceUntilIdle()

        // Then
        userListViewModel.singleEvent.test {
            expectMostRecentItem() shouldBe UserListEvent.OpenUserDetail(userName = userModels.first().name)
        }
    }

    private fun giveUserModelList(): List<UserModel> {
        val userModels = listOf(UserModel(id = 0, name = "UserModel 0"),UserModel(id = 1, name = "UserModel 1"), UserModel(id = 2, name = "UserModel 2"))
        coEvery { getUserListUseCase.invoke(page = 1, pageSize = 20) } returns userModels
        return userModels
    }

    private fun createUserListViewModel() = UserListViewModel(
        dispatcherProvider = dispatcherProvider,
        getUserListUseCase = getUserListUseCase,
        setFirstRunUseCase = setFirstRunUseCase,
        isFirstRunUseCase = isFirstRunUseCase,
    )
}