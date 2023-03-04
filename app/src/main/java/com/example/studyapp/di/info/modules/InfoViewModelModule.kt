package com.example.studyapp.di.info.modules

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.di.general.modules.NetworkModule
import com.example.studyapp.di.general.modules.ViewModelBinds
import com.example.studyapp.ui.info.viewmodels.InfoViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, ViewModelBinds::class])
class InfoViewModelModule {

    @Provides
    fun provideViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(owner)[InfoViewModel::class.java]
}