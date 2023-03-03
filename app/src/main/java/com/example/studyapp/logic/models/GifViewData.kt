package com.example.studyapp.logic.models

import com.example.studyapp.ui.main.models.GifModelUI

data class GifViewData(
    val id: String,
    val gif: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GifViewData

        if (!gif.contentEquals(other.gif)) return false

        return true
    }

    override fun hashCode(): Int {
        return gif.contentHashCode()
    }
}

