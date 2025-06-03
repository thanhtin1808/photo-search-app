package com.era.photosearch.domain.usecases.photos

import com.era.photosearch.domain.models.photos.UserDetailModel
import com.era.photosearch.domain.repositories.users.PhotoRepository
import javax.inject.Inject

class GetUserDetailUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
) {

    operator suspend fun invoke(userName: String): UserDetailModel = photoRepository.getUserDetail(userName)
}