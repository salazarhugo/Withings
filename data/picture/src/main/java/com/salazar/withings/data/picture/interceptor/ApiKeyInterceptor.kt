package com.salazar.withings.data.picture.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class ApiKeyInterceptor @Inject constructor(
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val originalUrl = request.url

        val modifiedUrl = originalUrl.newBuilder()
            .addQueryParameter(KEY_PARAMETER, API_KEY)
            .build()

        val modifiedRequest: Request = request.newBuilder()
            .url(modifiedUrl)
            .build()

        return chain.proceed(modifiedRequest)
    }

    companion object {
        private const val KEY_PARAMETER = "key"
        private const val API_KEY = "18021445-326cf5bcd3658777a9d22df6f"
    }
}
