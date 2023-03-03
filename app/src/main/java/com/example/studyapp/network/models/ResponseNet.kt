package com.example.studyapp.network.models

import com.example.studyapp.logic.models.GifSearchData

data class ResponseNet(
    val data: List<GifMetadataNet>,
    val pagination: PaginationNet,
    val meta: MetaNet
) {
    fun toModel(): List<GifSearchData> =
        this.data.map { it.toModel() }
}
