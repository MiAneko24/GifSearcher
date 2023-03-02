package com.example.studyapp.network.models

data class GifDataNet(
    val gif: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GifDataNet

        if (!gif.contentEquals(other.gif)) return false

        return true
    }

    override fun hashCode(): Int {
        return gif.contentHashCode()
    }
}
