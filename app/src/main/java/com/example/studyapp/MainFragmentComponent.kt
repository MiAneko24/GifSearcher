package com.example.studyapp

import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.ui.main.fragments.MainFragment
import com.example.studyapp.ui.main.viewmodels.MainViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [DaggerViewModelModule::class] )
interface MainFragmentComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(mainViewModel: MainViewModel)

    @Subcomponent.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance owner: ViewModelStoreOwner): MainFragmentComponent
    }

}