package com.example.studyapp.network.models

import com.example.studyapp.logic.models.GifData
import com.example.studyapp.logic.models.GifMetadata
import com.example.studyapp.logic.models.GifSearchData

data class InfoResponseNet(
    val data: GifMetadataNet,
    val pagination: PaginationNet,
    val meta: MetaNet
) {
    fun toModel(): GifSearchData =
        GifSearchData(
            data.toMetadata(),
            data.images.toModel()
        )
}
