package com.example.studyapp

import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.ui.main.fragments.GifInfoFragment
import com.example.studyapp.ui.main.fragments.MainFragment
import com.example.studyapp.ui.main.viewmodels.GifInfoViewModel
import com.example.studyapp.ui.main.viewmodels.MainViewModel
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Singleton

@Subcomponent(modules = [MainViewModelModule::class] )
interface MainFragmentComponent {

    fun inject(mainFragment: MainFragment)

    fun inject(mainViewModel: MainViewModel)

    @Subcomponent.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance owner: ViewModelStoreOwner): MainFragmentComponent
    }

}