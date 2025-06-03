package com.android.photosearch.features.users

import app.cash.turbine.test
import com.era.photosearch.compose.uistate.models.ErrorState
import com.era.photosearch.domain.models.errors.NoConnectionException
import com.era.photosearch.domain.models.photos.PhotoModel
import com.era.photosearch.domain.usecases.preferences.IsFirstRunUseCase
import com.era.photosearch.domain.usecases.preferences.SetFirstRunUseCase
import com.era.photosearch.domain.usecases.photos.GetPhotoListUseCase
import com.era.photosearch.features.photos.models.PhotoListEvent
import com.era.photosearch.features.photos.models.PhotoListUiState
import com.era.photosearch.providers.dispatchers.DispatcherProvider
import com.era.photosearch.features.photos.PhotoListViewModel
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
    private val getPhotoListUseCase: GetPhotoListUseCase = mockk()
    private val isFirstRunUseCase: IsFirstRunUseCase = mockk()
    private val setFirstRunUseCase: SetFirstRunUseCase = mockk()

    private lateinit var photoListViewModel: PhotoListViewModel

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
        photoListViewModel = createUserListViewModel()
        advanceUntilIdle()

        // Then
        photoListViewModel.uiState.test {
            expectMostRecentItem() shouldBe PhotoListUiState(photos = users.toPersistentList())
        }
    }

    @Test
    fun `getUserModelList is fail`() = runTest {
        val error = NoConnectionException()
        coEvery { getPhotoListUseCase(page = 1, pageSize = 20) } throws error

        // When
        photoListViewModel = createUserListViewModel()
        advanceUntilIdle()

        // Then
        photoListViewModel.error.test {
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
        photoListViewModel = createUserListViewModel()
        advanceUntilIdle()

        // Then
        photoListViewModel.singleEvent.test {
            expectMostRecentItem() shouldBe PhotoListEvent.FirstRun
        }
    }

    @Test
    fun `isFirstRun is false`() = runTest {
        // Given
        giveUserModelList()

        val expected = false
        every { isFirstRunUseCase() } returns flowOf(expected)

        // When
        photoListViewModel = createUserListViewModel()
        advanceUntilIdle()

        // Then
        photoListViewModel.singleEvent.test {
            expectNoEvents()
        }
    }

    @Test
    fun `openUserModelDetail calls`() = runTest {
        // Given
        val userModels = giveUserModelList()

        // When
        photoListViewModel = createUserListViewModel()
        advanceUntilIdle()

        photoListViewModel.openPhotoDetail(userModels.first())
        advanceUntilIdle()

        // Then
        photoListViewModel.singleEvent.test {
            expectMostRecentItem() shouldBe PhotoListEvent.OpenPhotoDetail(userName = userModels.first().name)
        }
    }

    private fun giveUserModelList(): List<PhotoModel> {
        val photoModels = listOf(PhotoModel(id = 0, name = "UserModel 0"),PhotoModel(id = 1, name = "UserModel 1"), PhotoModel(id = 2, name = "UserModel 2"))
        coEvery { getPhotoListUseCase.invoke(page = 1, pageSize = 20) } returns photoModels
        return photoModels
    }

    private fun createUserListViewModel() = PhotoListViewModel(
        dispatcherProvider = dispatcherProvider,
        getPhotoListUseCase = getPhotoListUseCase,
        setFirstRunUseCase = setFirstRunUseCase,
        isFirstRunUseCase = isFirstRunUseCase,
    )
}