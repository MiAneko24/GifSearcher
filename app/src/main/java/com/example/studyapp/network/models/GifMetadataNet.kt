package com.example.studyapp.network.models

import com.example.studyapp.logic.models.GifMetadata
import com.google.gson.annotations.SerializedName
import java.util.Date

data class GifMetadataNet(
    val type: String,
    val id: String,
    val url: String,
    val slug: String,

    @SerializedName("bitly_gif_url")
    val bitlyGifUrl: String,

    @SerializedName("bitly_url")
    val bitlyUrl: String,

    @SerializedName("embed_url")
    val embedUrl: String,
    val username: String,
    val source: String,
    val title: String,
    val rating: String,

    @SerializedName("content_url")
    val contentUrl: String,

    @SerializedName("source_tld")
    val sourceTld: String,

    @SerializedName("source_post_url")
    val sourcePostUrl: String,

    @SerializedName("is_sticker")
    val isSticker: Int,

    @SerializedName("import_datetime")
    val importDatetime: Date,

    @SerializedName("trending_datetime")
    val trendingDatetime: Date,
    val images: ImagesDataHolderNet,

    @SerializedName("analytics_response_payload")
    val analyticsResponsePayload: String
) {
    fun toModel(): GifMetadata =
        GifMetadata(
            type,
            id,
            url,
            username,
            source,
            title,
            rating,
            isSticker,
            importDatetime,
            trendingDatetime,
            images.toModel()
        )
}
