package com.salazar.withings.data.picture.mapper

import com.salazar.withings.data.picture.Picture
import com.salazar.withings.data.picture.api.response.HitsResponse

fun HitsResponse.toPicture(): Picture {
    return Picture(
        id = id,
        imageUrl = fullHDURL ?: largeImageURL ?: imageURL ?: previewURL,
        user = user,
        comments = comments,
        likes = likes,
        userAvatar = userImageURL,
    )
}