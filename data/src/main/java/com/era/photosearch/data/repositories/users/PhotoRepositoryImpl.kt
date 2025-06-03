package com.era.photosearch.data.repositories.users

import androidx.room.Query
import com.era.photosearch.data.remote.services.PhotoService
import com.era.photosearch.data.repositories.users.mappers.toPhotoList
import com.era.photosearch.data.repositories.users.mappers.toUserDetailModel
import com.era.photosearch.domain.models.photos.UserDetailModel
import com.era.photosearch.domain.models.photos.PhotoModel
import com.era.photosearch.domain.repositories.users.PhotoRepository
import javax.inject.Inject

internal class PhotoRepositoryImpl @Inject constructor(
    private val photoService: PhotoService,
) : PhotoRepository {

    override suspend fun searchPhoto(query: String, page: Int, pageSize: Int): List<PhotoModel> {
        val response = photoService.searchPhoto(query = query, page = page, perPage = pageSize)
        val photoList = response.photos?.toPhotoList().orEmpty()
        return photoList
    }

    override suspend fun getUserDetail(userName: String): UserDetailModel {
        val response = photoService.getUserDetail(loginUsername = userName)
        val userDetail = response.toUserDetailModel()
        return userDetail
    }
}

internal val sampleUserList = MutableList(6) { index ->
    PhotoModel(
        id = index.toLong(),
        originalSourceImage = "https://images.pexels.com/photos/32370562/pexels-photo-32370562.jpeg"
    )
}