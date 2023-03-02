package com.example.studyapp.network.models

import com.example.studyapp.logic.models.ImageMetadata

data class ImageMetadataNet(
    val height: String,
    val width: String,
    val size: String,
    val url: String,
    val mp4Size: String?,
    val mp4: String?,
    val webpSize: String?,
    val frames: String?,
    val hash: String?
) {
    fun toModel(): ImageMetadata =
        ImageMetadata(
            height,
            width,
            size,
            url
        )
}
