package com.example.studyapp.di.search.components

import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.di.search.modules.SearchViewModelModule
import com.example.studyapp.ui.search.fragments.SearchFragment
import com.example.studyapp.ui.search.viewmodels.MainViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [SearchViewModelModule::class] )
interface SearchComponent {

    fun inject(searchFragment: SearchFragment)

    fun inject(mainViewModel: MainViewModel)

    @Subcomponent.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance owner: ViewModelStoreOwner): SearchComponent
    }

}