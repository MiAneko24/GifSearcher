package com.example.studyapp.logic.models

import java.util.*

data class GifData(
    val id: String,
    val type: String,
    val rating: String,
    val title: String,
    val isSticker: Int,
    val url: String,
    val username: String,
    val source: String,
    val importDatetime: Date,
    val trendingDatetime: Date,
    val gif: ByteArray
)
