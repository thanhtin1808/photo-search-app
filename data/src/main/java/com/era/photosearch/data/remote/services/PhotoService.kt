package com.era.photosearch.data.remote.services

import com.android.photosearch.data.remote.models.responses.users.UserDetailResponse
import com.era.photosearch.data.remote.models.responses.BaseResponse
import com.era.photosearch.data.remote.models.responses.photos.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PhotoService {
    @GET("/v1/search")
    suspend fun searchPhoto(@Query("query") query: String = "", @Query("per_page") perPage: Int = 10, @Query("page") page: Int = 1): BaseResponse<List<PhotoResponse>>
    @GET("/users/{login_username}")
    suspend fun getUserDetail(@Path("login_username") loginUsername: String): UserDetailResponse
}