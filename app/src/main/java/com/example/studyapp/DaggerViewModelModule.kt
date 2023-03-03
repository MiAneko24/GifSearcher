package com.example.studyapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.logic.GifLoader
import com.example.studyapp.logic.GifLoaderImpl
import com.example.studyapp.network.*
import com.example.studyapp.ui.main.viewmodels.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, ViewModelBinds::class])
class DaggerViewModelModule {
    @Provides
    fun provideMainViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(owner)[MainViewModel::class.java]
}

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