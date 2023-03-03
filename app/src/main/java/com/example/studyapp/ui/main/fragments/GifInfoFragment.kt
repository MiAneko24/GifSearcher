package com.example.studyapp.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.studyapp.AppComponent
import com.example.studyapp.GifSearcherApplication
import com.example.studyapp.MainFragmentComponent
import com.example.studyapp.R
import com.example.studyapp.databinding.FragmentMainBinding
import com.example.studyapp.databinding.GifInfoFragmentBinding
import com.example.studyapp.ui.main.viewmodels.MainViewModel
import javax.inject.Inject

class GifInfoFragment(
    appComponent: AppComponent
): Fragment() {
    @Inject
    lateinit var viewModel: MainViewModel

    private val fragmentComponent: MainFragmentComponent =
        appComponent.mainFragmentComponent().create(this)


    private var _binding: GifInfoFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance(appComponent: AppComponent) =
            GifInfoFragment(appComponent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Creation", "GifInfo OnCreate()")

        fragmentComponent.inject(this)
        viewModel.applyComponent(fragmentComponent)

        viewModel.getInfoErrorMessage.observe(this) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
        

        setHasOptionsMenu(true)
        // TODO: Use the ViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar_menu, menu)
    }

}