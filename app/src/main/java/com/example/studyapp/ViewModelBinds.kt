package com.example.studyapp

import com.example.studyapp.logic.GifLoader
import com.example.studyapp.logic.GifLoaderImpl
import com.example.studyapp.network.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ViewModelBinds {

    @Binds
    fun provideGifVideoAPI(gifLoad: GifVideoAPIImpl): GifVideoAPI

    @Binds
    fun provideGifSearchAPI(gifSearch: GifSearchAPIImpl): GifSearchAPI

    @Binds
    fun provideGifInfoAPI(gifInfoAPI: GifInfoAPIImpl): GifInfoAPI

    @Binds
    fun provideGifLoader(gifLoader: GifLoaderImpl): GifLoader
}