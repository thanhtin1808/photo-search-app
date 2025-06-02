package com.era.photosearch.domain.usecases.users

import com.era.photosearch.domain.repositories.users.UserRepository
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(page: Int, pageSize: Int) = userRepository.getUserList(page, pageSize)
}
