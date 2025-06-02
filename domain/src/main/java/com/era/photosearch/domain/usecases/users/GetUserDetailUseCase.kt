package com.era.photosearch.domain.usecases.users

import com.era.photosearch.domain.models.users.UserDetailModel
import com.era.photosearch.domain.repositories.users.UserRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {

    operator suspend fun invoke(userName: String): UserDetailModel = userRepository.getUserDetail(userName)
}