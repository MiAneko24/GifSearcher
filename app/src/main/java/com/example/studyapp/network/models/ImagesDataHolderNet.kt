package com.example.studyapp.network.models

import com.example.studyapp.logic.models.ImageMetadata
import com.google.gson.annotations.SerializedName

data class ImagesDataHolderNet(
    val original: ImageMetadataNet,
    val downsized: ImageMetadataNet,

    @SerializedName("downsized_large")
    val downsizedLarge: ImageMetadataNet,

    @SerializedName("downsized_medium")
    val downsizedMedium: ImageMetadataNet,

    @SerializedName("downsized_small")
    val downsizedSmall: ImageMetadataNet,

    @SerializedName("downsized_still")
    val downsizedStill: ImageMetadataNet,

    @SerializedName("fixed_height")
    val fixedHeight: ImageMetadataNet,

    @SerializedName("fixed_height_downsampled")
    val fixedHeightDownsampled: ImageMetadataNet,

    @SerializedName("fixed_height_small")
    val fixedHeightSmall: ImageMetadataNet,

    @SerializedName("fixed_height_still")
    val fixedHeightStill: ImageMetadataNet,

    @SerializedName("fixed_width_downsampled")
    val fixedWidthDownsampled: ImageMetadataNet,

    @SerializedName("fixed_width_small")
    val fixedWidthSmall: ImageMetadataNet,

    @SerializedName("fixed_width_small_still")
    val fixedWidthSmallStill: ImageMetadataNet,

    @SerializedName("fixed_width_still")
    val fixedWidthStill: ImageMetadataNet,
    val looping: ImageMetadataNet,

    @SerializedName("original_still")
    val originalStill: ImageMetadataNet,
    val preview: ImageMetadataNet,

    @SerializedName("preview_gif")
    val previewGif: ImageMetadataNet,

    @SerializedName("preview_webp")
    val previewWebp: ImageMetadataNet,

    @SerializedName("480w_still")
    val w480Still: ImageMetadataNet
) {
    fun toModel(): ImageMetadata =
        downsizedMedium.toModel()
}
