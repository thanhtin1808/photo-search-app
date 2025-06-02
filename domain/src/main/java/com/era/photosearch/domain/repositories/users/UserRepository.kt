package com.era.photosearch.domain.repositories.users

import com.era.photosearch.domain.models.users.UserDetailModel
import com.era.photosearch.domain.models.users.UserModel

interface UserRepository {

    suspend fun getUserList(page: Int, pageSize: Int): List<UserModel>

    suspend fun getUserDetail(userName: String): UserDetailModel
}