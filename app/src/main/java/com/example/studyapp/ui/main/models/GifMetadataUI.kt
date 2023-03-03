package com.example.studyapp.ui.main.models

import com.example.studyapp.logic.models.GifMetadata
import java.util.*

data class GifMetadataUI(
    val id: String = "",
    val type: String = "",
    val rating: String = "",
    val title: String = "",
    val isSticker: Int = 0,
    val url: String = "",
    val username: String = "",
    val source: String = "",
    val importDatetime: Date = Date(),
    val trendingDatetime: Date = Date()
) {
    companion object {
        fun fromModel(metadata: GifMetadata) = GifMetadataUI(
            metadata.id,
            metadata.type,
            metadata.rating,
            metadata.title,
            metadata.isSticker,
            metadata.url,
            metadata.username,
            metadata.source,
            metadata.importDatetime,
            metadata.trendingDatetime
        )
    }
}


