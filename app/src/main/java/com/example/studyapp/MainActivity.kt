package com.example.studyapp

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.studyapp.ui.callbacks.CloseKeyboardCallback
import com.example.studyapp.ui.callbacks.FragmentStateChangeCallback
import com.example.studyapp.ui.callbacks.SetupToolbarCallback
import com.example.studyapp.ui.info.fragments.InfoFragment
import com.example.studyapp.ui.search.fragments.SearchFragment

class MainActivity :
    AppCompatActivity(),
    FragmentStateChangeCallback,
    SetupToolbarCallback,
    CloseKeyboardCallback
{

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Creation", "Activity OnCreate()")
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fragment = SearchFragment.newInstance()
            fragment.setCallbacks(
                this,
                this,
            this
            )

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commitNow()
        }

        setupToolbar()
    }

    fun setupToolbar() {
        toolbar = findViewById(R.id.app_toolbar)
        setSupportActionBar(toolbar)
        setButtonUpVisibility(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            callback(Action.Return)
        } else {
            Log.d("Options", "ItemId is ${item.itemId}")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun callback(action: Action) {
        when (action) {
            Action.ShowGifInformation -> openGifInfo()
            Action.Return -> restorePreviousFragment()
        }
    }

    private fun restorePreviousFragment() {
        if (supportFragmentManager
                .findFragmentById(R.id.container)
                ?.javaClass != SearchFragment::class.java) {
            supportFragmentManager.popBackStack()
        }
    }

    private fun openGifInfo() {
        if (supportFragmentManager
                .findFragmentById(R.id.container)
                ?.javaClass != InfoFragment::class.java) {
            val fragment = InfoFragment.newInstance()
            fragment.setCallback(this)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun setButtonUpVisibility(visible: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(visible)
    }

    override fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun close() {
        val focus = currentFocus
        if (focus != null) {
            val manager = getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager

            manager.hideSoftInputFromWindow(
                focus.windowToken, 0
            )
        }
    }
}