package com.example.studyapp.logic.models

import java.util.*

data class GifMetadata(
    val type: String,
    val id: String,
    val url: String,
    val username: String,
    val source: String,
    val title: String,
    val rating: String,
    val isSticker: Int,
    val importDatetime: Date,
    val trendingDatetime: Date,
    val image: ImageMetadata,
)
