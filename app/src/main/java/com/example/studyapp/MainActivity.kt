package com.example.studyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import com.example.studyapp.ui.callbacks.FragmentStateChangeCallback
import com.example.studyapp.ui.info.fragments.InfoFragment
import com.example.studyapp.ui.search.fragments.SearchFragment

class MainActivity : AppCompatActivity(), FragmentStateChangeCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Creation", "Activity OnCreate()")
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment = SearchFragment.newInstance()
            fragment.setCallback(this)

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }
//        setSupportActionBar(findViewById(R.id.app_toolbar))
    }

    override fun callback(action: Action) {
        when (action) {
            Action.ShowGifInformation -> openGifInfo()
        }
    }

    private fun openGifInfo() {
        if (supportFragmentManager
                .findFragmentById(R.id.container)
            ?.javaClass != InfoFragment::class.java
        ) {
            val fragment = InfoFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}