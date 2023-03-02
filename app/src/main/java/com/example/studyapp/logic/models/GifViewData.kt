package com.example.studyapp.logic.models

data class GifViewData(
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
