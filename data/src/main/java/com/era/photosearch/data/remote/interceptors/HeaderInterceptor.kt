package com.era.photosearch.data.remote.interceptors

import com.era.photosearch.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

internal class HeaderInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("Authentication", BuildConfig.BASE_URL)
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .build()
        return chain.proceed(request)
    }
}