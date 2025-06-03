package com.era.photosearch.domain.usecases.photos

import com.era.photosearch.domain.repositories.users.PhotoRepository
import javax.inject.Inject

class GetPhotoListUseCase @Inject constructor(
    private val photoRepository: PhotoRepository,
) {
    suspend operator fun invoke(query:String, page: Int, pageSize: Int) = photoRepository.searchPhoto(query, page, pageSize)
}
