package com.example.studyapp

import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.ui.main.fragments.GifInfoFragment
import com.example.studyapp.ui.main.viewmodels.GifInfoViewModel
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Singleton


@Subcomponent(modules = [GifInfoViewModelModule::class] )
interface GifInfoComponent {

    fun inject(gifInfoViewModel: GifInfoViewModel)

    fun inject(gifInfoFragment: GifInfoFragment)

    @Subcomponent.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance owner: ViewModelStoreOwner): GifInfoComponent
    }
}