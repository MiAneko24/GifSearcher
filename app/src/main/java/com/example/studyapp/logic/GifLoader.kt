package com.example.studyapp.logic

import com.example.studyapp.logic.models.GifData
import com.example.studyapp.logic.models.GifViewData
import com.example.studyapp.network.GifLoadAPI
import com.example.studyapp.network.GifSearchAPI
import com.example.studyapp.network.models.ResultType
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
    val gifLoad: GifLoadAPI
): GifLoader {
    override suspend fun search(keyword: String): ResultType<List<GifViewData>> {
        return when (val gifsData = gifSearch.search(keyword)) {
            is ResultType.Error -> ResultType.Error(gifsData.message)
            is ResultType.Ok -> {
                val bodies =
                    gifsData.value.pmap {
                            when (val gifBody = gifLoad.loadGif(it.image.url)) {
                                is ResultType.Error -> null
                                is ResultType.Ok -> GifViewData(it.id, gifBody.value)
                            }
                    }
                ResultType.Ok(bodies.filterNotNull())
            }
        }

    }

    override suspend fun searchOne(id: String): ResultType<GifData> {
        TODO("Not yet implemented")
    }
}