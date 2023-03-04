package com.example.studyapp.di.info.components

import androidx.lifecycle.ViewModelStoreOwner
import com.example.studyapp.di.info.modules.InfoViewModelModule
import com.example.studyapp.ui.info.fragments.InfoFragment
import com.example.studyapp.ui.info.viewmodels.InfoViewModel
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(modules = [InfoViewModelModule::class] )
interface InfoComponent {

    fun inject(infoViewModel: InfoViewModel)

    fun inject(infoFragment: InfoFragment)

    @Subcomponent.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance owner: ViewModelStoreOwner): InfoComponent
    }
}