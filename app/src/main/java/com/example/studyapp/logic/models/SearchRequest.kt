package com.example.studyapp.logic.models

data class SearchRequest(
    val keywords: String,
    val offset: Int,
    val limit: Int
)
