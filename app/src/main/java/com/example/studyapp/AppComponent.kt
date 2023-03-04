package com.example.studyapp

import com.example.studyapp.di.info.components.InfoComponent
import com.example.studyapp.di.general.modules.SubcomponentsModule
import com.example.studyapp.di.search.components.SearchComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SubcomponentsModule::class])
interface AppComponent {

    fun mainFragmentComponent(): SearchComponent.Factory

    fun gifInfoComponent(): InfoComponent.Factory
}