package com.era.photosearch.compose.uistate.mappers

import android.content.Context
import com.era.photosearch.domain.models.errors.ApiException
import com.era.photosearch.domain.models.errors.NoConnectionException
import com.era.photosearch.domain.models.errors.UnauthorizedException
import com.era.photosearch.R

fun Throwable?.toReadableMessage(context: Context): String {
    return when (this) {
        is NoConnectionException -> context.getString(R.string.error_message_no_internet_connection)
        is UnauthorizedException -> context.getString(R.string.error_message_unauthorized)
        is ApiException -> message
        else -> context.getString(R.string.error_message_generic)
    }
}