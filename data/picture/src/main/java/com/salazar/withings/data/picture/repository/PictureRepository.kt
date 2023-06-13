package com.salazar.withings.data.picture.repository

import com.salazar.withings.data.picture.Picture

interface PictureRepository {
    suspend fun searchPictures(
        query: String
    ): Result<List<Picture>>
}