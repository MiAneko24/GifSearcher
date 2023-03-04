package com.example.studyapp.di.general.modules

import com.example.studyapp.di.info.components.InfoComponent
import com.example.studyapp.di.search.components.SearchComponent
import dagger.Module

@Module(subcomponents = [SearchComponent::class, InfoComponent::class])
class SubcomponentsModule {}