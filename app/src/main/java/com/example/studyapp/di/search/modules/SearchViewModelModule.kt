package com.example.studyapp.di.search.modules

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.di.general.modules.NetworkModule
import com.example.studyapp.di.general.modules.ViewModelBinds
import com.example.studyapp.ui.search.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class, ViewModelBinds::class])
class SearchViewModelModule {
    @Provides
    fun provideMainViewModel(owner: ViewModelStoreOwner) =
        ViewModelProvider(owner)[MainViewModel::class.java]
}
