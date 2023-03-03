package com.example.studyapp.ui.main.models

import com.example.studyapp.logic.models.GifData
import com.example.studyapp.logic.models.GifViewData

data class GifModelUI(
    val id: String,
    val gif: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GifModelUI

        if (!gif.contentEquals(other.gif)) return false

        return true
    }

    override fun hashCode(): Int {
        return gif.contentHashCode()
    }

    companion object {
        fun fromModel(model: GifViewData) = GifModelUI(model.id, model.gif)
    }
}
