package com.example.studyapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.logic.GifLoader
import com.example.studyapp.logic.GifLoaderImpl
import com.example.studyapp.network.GifLoadAPI
import com.example.studyapp.network.GifLoadAPIImpl
import com.example.studyapp.network.GifSearchAPI
import com.example.studyapp.network.GifSearchAPIImpl
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
    fun provideGifLoadAPI(gifLoad: GifLoadAPIImpl): GifLoadAPI

    @Binds
    fun provideGifSearchAPI(gifSearch: GifSearchAPIImpl): GifSearchAPI

    @Binds
    fun provideGifLoader(gifLoader: GifLoaderImpl): GifLoader
}