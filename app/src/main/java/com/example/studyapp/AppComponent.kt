package com.example.studyapp

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SubcomponentsModule::class])
interface AppComponent {

    fun mainFragmentComponent(): MainFragmentComponent.Factory
}