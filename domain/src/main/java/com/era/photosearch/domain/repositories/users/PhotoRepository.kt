package com.era.photosearch.domain.repositories.users

import com.era.photosearch.domain.models.photos.UserDetailModel
import com.era.photosearch.domain.models.photos.PhotoModel

interface PhotoRepository {

    suspend fun searchPhoto(query: String, page: Int, pageSize: Int): List<PhotoModel>

    suspend fun getUserDetail(userName: String): UserDetailModel
}