package com.example.studyapp.logic

import com.example.studyapp.logic.models.GifViewData
import com.example.studyapp.network.GifLoadAPI
import com.example.studyapp.network.GifSearchAPI
import com.example.studyapp.network.models.ResultType
import javax.inject.Inject

interface GifLoader {
    suspend fun search(keyword: String): ResultType<List<GifViewData>>
}

class GifLoaderImpl @Inject constructor(
    val gifSearch: GifSearchAPI,
    val gifLoad: GifLoadAPI
): GifLoader {
    override suspend fun search(keyword: String): ResultType<List<GifViewData>> {
        return when (val gifsData = gifSearch.search(keyword)) {
            is ResultType.Error -> ResultType.Error(gifsData.message)
            is ResultType.Ok -> {
                val bodies = gifsData.value.map {
                    when (val gifBody = gifLoad.loadGif(it.image.url)) {
                        is ResultType.Error -> null
                        is ResultType.Ok -> GifViewData(gifBody.value)
                    }
                }
                ResultType.Ok(bodies.filterNotNull())
            }
        }

    }
}