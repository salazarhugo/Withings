package com.salazar.withings.data.picture.api.response

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class SearchImageRequest(
    val packageName: String,
    val productId: String,
    val purchaseToken: String,
)
