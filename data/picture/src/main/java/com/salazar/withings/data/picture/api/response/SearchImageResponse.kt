package com.salazar.withings.data.picture.api.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SearchImageResponse(
    val total: Int,
    val totalHits: Int,
    val hits: List<HitsResponse>,
)

@JsonClass(generateAdapter = true)
data class HitsResponse(
    val id: String,
    val imageURL: String?,
    val previewURL: String,
    val likes: Int,
    val comments: Int,
    val user: String,
)
