package com.salazar.withings.data.picture.api

import com.salazar.withings.data.picture.api.response.SearchImageResponse
import retrofit2.http.*


interface PixabayApi {

    @Headers("Content-Type: application/json")
    @GET("/api")
    suspend fun searchImage(
        @Query("q") query: String,
        @Query("image_type") imageType: String = "photo",
    ): SearchImageResponse

    companion object {
        const val BASE_URL = "https://pixabay.com/"
    }
}