package com.example.studyapp.network

import android.util.Log
import com.example.studyapp.Info
import com.example.studyapp.logic.models.GifData
import com.example.studyapp.logic.models.GifMetadata
import com.example.studyapp.network.models.InfoResponseNet
import com.example.studyapp.network.models.ResponseNet
import com.example.studyapp.network.models.ResultType
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

interface GifInfoAPI {
    suspend fun getInformation(id: String): ResultType<GifMetadata>
}

class GifInfoAPIImpl @Inject constructor(
    @Info val client: OkHttpClient,
    val gson: Gson
): GifInfoAPI {
    override suspend fun getInformation(id: String): ResultType<GifMetadata> {
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