package com.example.studyapp.network.models

import com.example.studyapp.logic.models.SearchRequest

data class SearchRequestNet(
    val keywords: String,
    val offset: Int,
    val limit: Int
) {
    companion object {
        fun fromModel(model: SearchRequest) =
            SearchRequestNet(
                model.keywords,
                model.offset,
                model.limit
            )
    }
}
