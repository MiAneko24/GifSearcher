package com.example.studyapp.ui.models

import com.example.studyapp.logic.models.SearchRequest

data class SearchRequestUI(
    val keywords: String,
    val offset: Int = 0,
    val limit: Int = 25
) {
    fun toModel() = SearchRequest(
        keywords, offset, limit
    )
}
