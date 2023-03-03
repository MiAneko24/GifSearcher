package com.example.studyapp

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.ui.main.viewmodels.GifInfoViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [NetworkModule::class, ViewModelBinds::class])
class GifInfoViewModelModule {

    @Provides
    fun provideViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(owner)[GifInfoViewModel::class.java]
}