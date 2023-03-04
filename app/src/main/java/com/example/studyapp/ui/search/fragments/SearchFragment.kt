package com.example.studyapp.ui.search.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.*
import com.example.studyapp.databinding.SearchFragmentBinding
import com.example.studyapp.di.search.components.SearchComponent
import com.example.studyapp.ui.callbacks.FragmentStateChangeCallback
import com.example.studyapp.ui.search.GifSelectionListener
import com.example.studyapp.ui.search.recycler_elems.GifAdapter
import com.example.studyapp.ui.search.viewmodels.MainViewModel
import javax.inject.Inject


class SearchFragment: Fragment(), GifSelectionListener {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var fragmentComponent: SearchComponent

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GifAdapter
    private lateinit var callback: FragmentStateChangeCallback

    private var _binding: SearchFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SearchFragment()
    }

    fun setCallback(stateChangeCallback: FragmentStateChangeCallback) {
        callback = stateChangeCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Creation", "MainFragment OnCreate()")

        setHasOptionsMenu(true)
        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("Creation", "OnAttach called")

        val _activity = activity as MainActivity
        val _application = _activity.application as GifSearcherApplication

        fragmentComponent = _application.appComponent
            .mainFragmentComponent()
            .create(this)

        fragmentComponent.inject(this)
        viewModel.applyComponent(fragmentComponent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.gifImages.observe(viewLifecycleOwner) {
            adapter.replaceGifs(it)
        }

        viewModel.searchErrorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        _binding = SearchFragmentBinding.inflate(inflater, container, false)

        recyclerView = binding.recyclerContainer
        recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        adapter = GifAdapter()
        adapter.setGifSelectionListener(this)
        recyclerView.setItemViewCacheSize(10)
        recyclerView.adapter = adapter

        binding.input.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(textView)
            }
            return@setOnEditorActionListener true
        }

        binding.searchButton.setOnClickListener {
            search(binding.input)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appToolbarLayout.appToolbar.inflateMenu(R.menu.appbar_menu)
        binding.appToolbarLayout.appToolbar.title = getString(R.string.app_name)
    }

    fun search(textView: TextView) {
        val searchString: String = textView.text.toString()
        if (searchString.isEmpty()) {
            Toast.makeText(context, "Нечего искать", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Soon there will be search for $searchString", Toast.LENGTH_SHORT).show()
            viewModel.search(searchString)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun selected(position: Int) {
        if (viewModel.setGifPosition(position))
            callback.callback(Action.ShowGifInformation)
    }

}