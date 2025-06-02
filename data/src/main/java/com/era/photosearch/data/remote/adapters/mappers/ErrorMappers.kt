package com.android.photosearch.data.remote.adapters.mappers

import com.android.photosearch.data.remote.models.responses.errors.ErrorResponse
import com.era.photosearch.domain.models.errors.ApiException
import com.era.photosearch.domain.models.errors.UnauthorizedException
import com.era.photosearch.domain.utils.orEmpty
import com.era.photosearch.domain.utils.orZero
import com.google.gson.Gson
import retrofit2.Response
import timber.log.Timber
import java.net.HttpURLConnection

internal fun Response<*>.toThrowable(): Throwable {
    if (code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
        return UnauthorizedException()
    }
    val jsonString = errorBody()?.string()
    val errorResponse = try {
        val gson = Gson()
        gson.fromJson(jsonString, ErrorResponse::class.java)
    } catch (exception: Exception) {
        Timber.e(exception, "Error parsing: \n$jsonString")
        return exception
    }
    return ApiException(
        code = errorResponse?.code.orZero(),
        message = errorResponse?.message.orEmpty(),
    )
}