package com.example.studyapp.ui.main.fragments

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
import com.example.studyapp.GifSearcherApplication
import com.example.studyapp.MainFragmentComponent
import com.example.studyapp.databinding.FragmentMainBinding
import com.example.studyapp.ui.main.recycler_elems.GifAdapter
import com.example.studyapp.ui.main.viewmodels.MainViewModel
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    lateinit var fragmentComponent: MainFragmentComponent

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GifAdapter

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentComponent = (activity?.application as GifSearcherApplication)
            .appComponent
            .mainFragmentComponent()
            .create(this)

        fragmentComponent.inject(this)
        viewModel.applyComponent(fragmentComponent)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel.gifs.observe(viewLifecycleOwner) {
            adapter.replaceGifs(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        _binding = FragmentMainBinding.inflate(inflater, container, false)


        recyclerView = binding.recyclerContainer
        recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        adapter = GifAdapter()
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

}