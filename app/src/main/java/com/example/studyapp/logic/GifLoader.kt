package com.example.studyapp.logic

import android.util.Log
import com.example.studyapp.logic.models.GifData
import com.example.studyapp.logic.models.GifViewData
import com.example.studyapp.network.GifInfoAPI
import com.example.studyapp.network.GifVideoAPI
import com.example.studyapp.network.GifSearchAPI
import com.example.studyapp.ResultType
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

interface GifLoader {
    suspend fun search(keyword: String): ResultType<List<GifViewData>>

    suspend fun searchOne(id: String): ResultType<GifData>
}

suspend fun <A, B> Iterable<A>.pmap(f: suspend (A) -> B): List<B> = coroutineScope {
    map { async { f(it) } }.awaitAll()
}

class GifLoaderImpl @Inject constructor(
    val gifSearch: GifSearchAPI,
    val gifLoad: GifVideoAPI,
    val gifInfoAPI: GifInfoAPI
): GifLoader {
    override suspend fun search(keyword: String): ResultType<List<GifViewData>> {
        return when (val gifsData = gifSearch.search(keyword)) {
            is ResultType.Error -> {
                Log.e("Gif", "Search failed")
                ResultType.Error(gifsData.message)
            }
            is ResultType.Ok -> {
                val bodies =
                    gifsData.value.pmap {
                            when (val gifBody = gifLoad.loadGif(it.image.url)) {
                                is ResultType.Error -> {
                                    Log.e("Gif", "Could not load certain gif info")
                                    null
                                }
                                is ResultType.Ok -> GifViewData(it.metadata.id, gifBody.value)
                            }
                    }
                ResultType.Ok(bodies.filterNotNull())
            }
        }

    }

    override suspend fun searchOne(id: String): ResultType<GifData> {
        Log.d("Tag", "In searchOne")
        return when (val gifData = gifInfoAPI.getInformation(id)) {
            is ResultType.Error -> {
                Log.e("Gif", "Get Info failed")
                ResultType.Error(gifData.message)
            }
            is ResultType.Ok -> {

                Log.d("Net", "Download link: ${gifData.value.image.url}")
                when (val gifBody = gifLoad.loadGif(gifData.value.image.url)) {
                    is ResultType.Error -> {
                        Log.e("Gif", "Could not load body for certain gif info")
                        ResultType.Error(gifBody.message)
                    }
                    is ResultType.Ok -> {
                        Log.d("Net", "We've got body")
                        ResultType.Ok(
                            GifData(gifData.value.metadata, gifBody.value)
                        )
                    }
                }
            }
        }
    }
}