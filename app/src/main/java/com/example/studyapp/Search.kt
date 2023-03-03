package com.example.studyapp

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Search

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Load

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Info