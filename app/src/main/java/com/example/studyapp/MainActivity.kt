package com.example.studyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import androidx.fragment.app.Fragment
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
            val fragment = MainFragment.newInstance()
            fragment.setDI(appComponent)
            fragment.setCallback(this)

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        } else {
            val fragment = supportFragmentManager
                .findFragmentById(R.id.container)
            fragment?.let { setupFragment(it) }
        }

//        setSupportActionBar(findViewById(R.id.app_toolbar))
    }

    private fun setupFragment(fragment: Fragment) {
        when (fragment) {
            is MainFragment -> {
                fragment.setDI(appComponent)
                fragment.setCallback(this)
            }
            is GifInfoFragment -> {
                fragment.setDI(appComponent)
            }
        }
    }

    override fun callback(action: Action) {
        when (action) {
            Action.ShowGifInformation -> openGifInfo()
        }
    }

    private fun openGifInfo() {
        if (supportFragmentManager
                .findFragmentById(R.id.container)
            ?.javaClass != GifInfoFragment::class.java
        ) {
            val fragment = GifInfoFragment.newInstance()
            fragment.setDI(appComponent)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}