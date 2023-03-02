package com.example.studyapp.network.models

import com.google.gson.annotations.SerializedName

data class MetaNet(
    val status: Int,
    val msg: String,

    @SerializedName("response_id")
    val responseId: String
)
