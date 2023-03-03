package com.example.studyapp

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class NetworkModule {
    @Provides
    @Search
    fun provideSearchOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Load
    fun provideLoadOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Info
    fun bindInfoOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .setDateFormat("yyy-MM-dd HH:mm:ss")
            .create()
}