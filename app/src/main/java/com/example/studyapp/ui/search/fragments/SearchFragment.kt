package com.example.studyapp.ui.search.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studyapp.Action
import com.example.studyapp.GifSearcherApplication
import com.example.studyapp.MainActivity
import com.example.studyapp.R
import com.example.studyapp.databinding.SearchFragmentBinding
import com.example.studyapp.di.search.components.SearchComponent
import com.example.studyapp.ui.callbacks.CloseKeyboardCallback
import com.example.studyapp.ui.callbacks.FragmentStateChangeCallback
import com.example.studyapp.ui.callbacks.SetupToolbarCallback
import com.example.studyapp.ui.models.SearchRequestUI
import com.example.studyapp.ui.search.GifSelectionListener
import com.example.studyapp.ui.search.recycler_elems.GifAdapter
import com.example.studyapp.ui.search.viewmodels.MainViewModel
import javax.inject.Inject


class SearchFragment : Fragment(), GifSelectionListener {

    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var fragmentComponent: SearchComponent

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GifAdapter
    private lateinit var stateCallback: FragmentStateChangeCallback
    private lateinit var toolbarCallback: SetupToolbarCallback
    private lateinit var keyboardCallback: CloseKeyboardCallback

    private var loadingMore: Boolean = false
    private var _binding: SearchFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = SearchFragment()
    }

    fun setCallbacks(
        stateChangeCallback: FragmentStateChangeCallback,
        setupToolbarCallback: SetupToolbarCallback,
        closeKeyboardCallback: CloseKeyboardCallback
    ) {
        stateCallback = stateChangeCallback
        toolbarCallback = setupToolbarCallback
        keyboardCallback = closeKeyboardCallback
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        val _activity = activity as MainActivity
        val _application = _activity.application as GifSearcherApplication

        fragmentComponent = _application.appComponent
            .mainFragmentComponent()
            .create(this)

        fragmentComponent.inject(this)
        viewModel.applyComponent(fragmentComponent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        checkCallbacks()
        setupToolbar()
        setupRecyclerView(inflater, container)

        viewModel.gifImages.observe(viewLifecycleOwner) {
            if (binding.searchInProgressLayout.visibility == View.VISIBLE) {
                binding.searchInProgressLayout.visibility = View.GONE
                binding.recyclerContainer.visibility = View.VISIBLE
            }
            adapter.replaceGifs(it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        viewModel.searchErrorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        binding.input.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                enterText(textView)
            }
            return@setOnEditorActionListener true
        }

        binding.searchButton.setOnClickListener {
            enterText(binding.input)
        }

        return binding.root
    }

    private fun checkCallbacks() {
        if (!this::toolbarCallback.isInitialized ||
            !this::stateCallback.isInitialized ||
            !this::keyboardCallback.isInitialized
        ) {
            val _activity = activity as MainActivity
            setCallbacks(
                _activity,
                _activity,
                _activity
            )
        }
    }

    private fun setupToolbar() {
        toolbarCallback.setButtonUpVisibility(false)
        toolbarCallback.setTitle(getString(R.string.app_name))
    }

    private fun setupRecyclerView(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        binding.progressBar.visibility = View.GONE

        recyclerView = binding.recyclerContainer
        recyclerView.layoutManager = GridLayoutManager(
            context,
            resources.getInteger(R.integer.columns),
            RecyclerView.VERTICAL,
            false
        )
        adapter = GifAdapter()
        adapter.setGifSelectionListener(this)
        recyclerView.setItemViewCacheSize(10)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                loadingMore = true
                val linearLayoutManager = recyclerView.layoutManager as GridLayoutManager
                val loaded = adapter.itemCount
                val keywords = viewModel.searchKeywords.value

                if (loaded != 0 && keywords != null &&
                    linearLayoutManager.findLastCompletelyVisibleItemPosition() ==
                    loaded - 1
                ) {
                    binding.progressBar.visibility = View.VISIBLE
                    search(
                        SearchRequestUI(
                            keywords,
                            loaded + resources.getInteger(R.integer.limit)
                        )
                    )
                }
            }
        })

    }

    fun enterText(textView: TextView) {
        loadingMore = false
        val searchString: String = textView.text.toString()
        if (searchString.isEmpty()) {
            Toast.makeText(context, "Request is void", Toast.LENGTH_SHORT).show()
        } else {
            search(SearchRequestUI(searchString))
        }
    }

    private fun search(searchRequestUI: SearchRequestUI) {
        keyboardCallback.close()
        if (!loadingMore) {
            binding.searchInProgressLayout.visibility = View.VISIBLE
            binding.recyclerContainer.visibility = View.GONE
        }

        Toast.makeText(context, "Loading", Toast.LENGTH_LONG).show()
        viewModel.search(searchRequestUI)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun selected(position: Int) {
        if (viewModel.setGifPosition(position))
            stateCallback.callback(Action.ShowGifInformation)
    }

}