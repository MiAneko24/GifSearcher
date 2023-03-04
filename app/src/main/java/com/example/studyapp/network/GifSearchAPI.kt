package com.example.studyapp.network

import com.example.studyapp.ResultType
import com.example.studyapp.di.Search
import com.example.studyapp.logic.models.GifSearchData
import com.example.studyapp.network.models.ResponseNet
import com.example.studyapp.network.models.SearchRequestNet
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

interface GifSearchAPI {
    suspend fun search(searchRequest: SearchRequestNet): ResultType<List<GifSearchData>>
}

class GifSearchAPIImpl @Inject constructor(
    @Search val client: OkHttpClient,
    val gson: Gson
) : GifSearchAPI {

    override suspend fun search(searchRequest: SearchRequestNet): ResultType<List<GifSearchData>> {
        val key = searchRequest.keywords.replace(' ', '+')
        return try {
            val request = Request.Builder()
                .url(
                    "https://api.giphy.com/v1/gifs/search?" +
                            "api_key=ny94DHSx8K6JFaVh8Es2jx96ZuaOPcSf" +
                            "&q=$key" +
                            "&limit=${searchRequest.limit}" +
                            "&offset=${searchRequest.offset}" +
                            "&rating=g&lang=en"
                )
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