package com.salazar.withings.data.picture.repository

import com.salazar.withings.data.picture.Picture
import com.salazar.withings.data.picture.api.PixabayApi
import com.salazar.withings.data.picture.mapper.toPicture
import javax.inject.Inject

class PictureRepositoryImpl @Inject constructor(
    private val service: PixabayApi,
): PictureRepository {
    override suspend fun searchPictures(
        query: String,
    ): Result<List<Picture>> {
        return try {
            val result = service.searchImage(
                query = query
            )
            val pictures = result.hits.map {
                it.toPicture()
            }
            Result.success(pictures)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}