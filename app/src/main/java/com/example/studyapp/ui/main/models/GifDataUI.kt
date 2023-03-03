package com.example.studyapp.ui.main.models

import com.example.studyapp.logic.models.ImageMetadata
import java.util.*

data class GifDataUI(
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
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GifDataUI

        if (id != other.id) return false
        if (type != other.type) return false
        if (rating != other.rating) return false
        if (title != other.title) return false
        if (isSticker != other.isSticker) return false
        if (url != other.url) return false
        if (username != other.username) return false
        if (source != other.source) return false
        if (importDatetime != other.importDatetime) return false
        if (trendingDatetime != other.trendingDatetime) return false
        if (!gif.contentEquals(other.gif)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + rating.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + isSticker
        result = 31 * result + url.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + source.hashCode()
        result = 31 * result + importDatetime.hashCode()
        result = 31 * result + trendingDatetime.hashCode()
        result = 31 * result + gif.contentHashCode()
        return result
    }
}
