package com.example.studyapp.network

import com.example.studyapp.Load
import com.example.studyapp.network.models.ResultType
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

interface GifLoadAPI {
    suspend fun loadGif(url: String): ResultType<ByteArray>
}

class GifLoadAPIImpl @Inject constructor(
    @Load val client: OkHttpClient
): GifLoadAPI {
    override suspend fun loadGif(url: String): ResultType<ByteArray> {
        return try {
            val request = Request.Builder()
                .url(url)
                .build()

            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val data = response.body?.bytes()
                if (data == null) {
                    ResultType.Error("Empty data")
                } else {
                    ResultType.Ok(data)
                }
            } else {
                ResultType.Error(response.message)
            }
        } catch (error: Error) {
            ResultType.Error(error.message.toString())
        }
    }

}