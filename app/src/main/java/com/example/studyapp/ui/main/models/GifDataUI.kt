package com.example.studyapp.ui.main.models

import com.example.studyapp.logic.models.GifData

data class GifDataUI(
    val gifMetadataUI: GifMetadataUI = GifMetadataUI(),
    val gif: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GifDataUI

        if (gifMetadataUI != other.gifMetadataUI) return false
        if (!gif.contentEquals(other.gif)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = gifMetadataUI.hashCode()
        result = 31 * result + gif.contentHashCode()
        return result
    }

    companion object {
        fun fromModel(model: GifData) =
            GifDataUI(
                GifMetadataUI.fromModel(model.gifMetadata),
                model.gif
            )
    }
}

