package com.example.studyapp.ui.main.fragments

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
import com.example.studyapp.databinding.FragmentMainBinding
import com.example.studyapp.ui.main.recycler_elems.GifAdapter
import com.example.studyapp.ui.main.viewmodels.MainViewModel
import javax.inject.Inject

class MainFragment(
    appComponent: AppComponent,
    private val callback: FragmentStateChangeCallback
) : Fragment(), GifSelectionListener {

    @Inject
    lateinit var viewModel: MainViewModel

    private val fragmentComponent: MainFragmentComponent =
        appComponent.mainFragmentComponent().create(this)

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GifAdapter

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance(
            component: AppComponent,
            callback: FragmentStateChangeCallback
        ) = MainFragment(component, callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Creation", "MainFragment OnCreate()")

        fragmentComponent.inject(this)
        viewModel.applyComponent(fragmentComponent)

        setHasOptionsMenu(true)
        // TODO: Use the ViewModel
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
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        _binding = FragmentMainBinding.inflate(inflater, container, false)

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
        binding.appToolbar.appToolbar.inflateMenu(R.menu.appbar_menu)
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
        viewModel.getGifInfo(position)
        callback.callback(Action.ShowGifInformation)
    }

}