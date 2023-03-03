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
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, ViewModelBinds::class])
class MainViewModelModule {
    @Provides
    fun provideMainViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(owner)[MainViewModel::class.java]
}
