package com.example.studyapp

import android.app.Application

class GifSearcherApplication: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()
    }
//    val appComponent: AppComponent =
}