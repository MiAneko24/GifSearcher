package com.example.studyapp.network

import com.example.studyapp.Search
import com.example.studyapp.logic.models.GifSearchData
import com.example.studyapp.network.models.ResponseNet
import com.example.studyapp.network.models.ResultType
import com.google.gson.Gson
import okhttp3.*
import javax.inject.Inject

interface GifSearchAPI {
    suspend fun search(keyword: String): ResultType<List<GifSearchData>>
}

class GifSearchAPIImpl @Inject constructor(
    @Search val client: OkHttpClient,
    val gson: Gson
) : GifSearchAPI {

    override suspend fun search(keyword: String): ResultType<List<GifSearchData>> {
        val key  = keyword.replace(' ', '+')
        return try {
            val request = Request.Builder()
                .url("https://api.giphy.com/v1/gifs/search?api_key=ny94DHSx8K6JFaVh8Es2jx96ZuaOPcSf&q=$key&limit=25&offset=0&rating=g&lang=en")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val data = gson.fromJson(response.body?.string(), ResponseNet::class.java)
                ResultType.Ok(data.toModel())
            } else {
                ResultType.Error(response.message)
            }
        } catch (error: Error) {
            ResultType.Error(error.message.toString())
        }
    }
}