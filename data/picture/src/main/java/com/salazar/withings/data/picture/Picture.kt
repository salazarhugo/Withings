package com.salazar.withings.data.picture

data class Picture(
    val id: String,
    val imageUrl: String,
    val likes: Int,
    val comments: Int,
    val user: String,
)
