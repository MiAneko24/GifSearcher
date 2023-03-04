package com.example.studyapp.network

import android.util.Log
import com.example.studyapp.di.Info
import com.example.studyapp.logic.models.GifSearchData
import com.example.studyapp.network.models.InfoResponseNet
import com.example.studyapp.ResultType
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

interface GifInfoAPI {
    suspend fun getInformation(id: String): ResultType<GifSearchData>
}

class GifInfoAPIImpl @Inject constructor(
    @Info val client: OkHttpClient,
    val gson: Gson
): GifInfoAPI {
    override suspend fun getInformation(id: String): ResultType<GifSearchData> {
        return try {
            Log.d("Net", "Getting info for $id")

            val request = Request.Builder()
                .url("https://api.giphy.com/v1/gifs/$id?api_key=ny94DHSx8K6JFaVh8Es2jx96ZuaOPcSf")
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val data = gson.fromJson(response.body?.string(), InfoResponseNet::class.java)
                ResultType.Ok(data.toModel())
            } else {
                ResultType.Error(response.message)
            }
        } catch (error: Error) {
            ResultType.Error(error.message.toString())
        }
    }

}