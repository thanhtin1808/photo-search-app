package com.android.photosearch.data.repositories.users

import com.android.photosearch.data.remote.services.UserService
import com.android.photosearch.data.repositories.users.mappers.toUserDetailModel
import com.android.photosearch.data.repositories.users.mappers.toUserList
import com.era.photosearch.domain.models.users.UserDetailModel
import com.era.photosearch.domain.models.users.UserModel
import com.era.photosearch.domain.repositories.users.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
) : UserRepository {

    override suspend fun getUserList(page: Int, pageSize: Int): List<UserModel> {
        val response = userService.getUserList(page = page, perPage = pageSize)
        val userList = response.toUserList()
        return userList
    }

    override suspend fun getUserDetail(userName: String): UserDetailModel {
        val response = userService.getUserDetail(loginUsername = userName)
        val userDetail = response.toUserDetailModel()
        return userDetail
    }
}

internal val sampleUserList = MutableList(6) { index ->
    UserModel(
        id = index,
        avatar = "",
        name = "David ${index +1}",
        landingPageUrl = "https://www.linkedin.com/"
    )
}