package com.example.studyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import com.example.studyapp.ui.main.fragments.GifInfoFragment
import com.example.studyapp.ui.main.fragments.MainFragment

class MainActivity : AppCompatActivity(), FragmentStateChangeCallback {

    private lateinit var appComponent: AppComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Creation", "Activity OnCreate()")
        appComponent = (application as GifSearcherApplication).appComponent
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(
                    appComponent,
                    this
                    )
                )
                .commitNow()
        }

//        setSupportActionBar(findViewById(R.id.app_toolbar))
    }

    override fun callback(action: Action) {
        when (action) {
            Action.ShowGifInformation -> openGifInfo()
        }
    }

    fun openGifInfo() {
        if (supportFragmentManager
                .findFragmentById(R.id.container)
            ?.javaClass != GifInfoFragment::class.java
        ) {
            val fragment = GifInfoFragment.newInstance(
                appComponent
            )

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
    }
}