package com.example.studyapp

import dagger.Module
import javax.inject.Singleton

@Module(subcomponents = [MainFragmentComponent::class, GifInfoComponent::class])
class SubcomponentsModule