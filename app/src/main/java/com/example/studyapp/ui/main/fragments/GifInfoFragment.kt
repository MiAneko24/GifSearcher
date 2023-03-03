package com.example.studyapp.ui.main.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.studyapp.AppComponent
import com.example.studyapp.MainFragmentComponent
import com.example.studyapp.R
import com.example.studyapp.databinding.GifInfoFragmentBinding
import com.example.studyapp.ui.main.viewmodels.MainViewModel
import javax.inject.Inject

class GifInfoFragment: Fragment() {
    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var fragmentComponent: MainFragmentComponent


    private var _binding: GifInfoFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = GifInfoFragment()
    }

    fun setDI(appComponent: AppComponent) {
        fragmentComponent = appComponent.mainFragmentComponent().create(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Creation", "GifInfo OnCreate()")

        fragmentComponent.inject(this)
        viewModel.applyComponent(fragmentComponent)

        setHasOptionsMenu(true)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getInfoErrorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

        viewModel.currentGif.observe(viewLifecycleOwner) {
            Glide.with(binding.gifView).load(it.gif).into(binding.gifView)
            binding.idValue.text = it.gifMetadataUI.id
            binding.titleValue.text = it.gifMetadataUI.title
            binding.urlValue.text = it.gifMetadataUI.url
            binding.ratingValue.text = it.gifMetadataUI.rating
            binding.sourceValue.text = it.gifMetadataUI.source
            binding.typeValue.text = it.gifMetadataUI.type
            binding.userValue.text = it.gifMetadataUI.username
            binding.stickerValue.text = it.gifMetadataUI.isSticker.toString()
            binding.importValue.text = it.gifMetadataUI.importDatetime.toString()
            binding.trendingValue.text = it.gifMetadataUI.trendingDatetime.toString()
            binding.appToolbarGifLayout.appToolbar.title = it.gifMetadataUI.title
        }

        _binding = GifInfoFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.appToolbarGifLayout.appToolbar.inflateMenu(R.menu.appbar_menu)
        binding.appToolbarGifLayout.appToolbar.title = getString(R.string.gif_info)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.appbar_menu, menu)
    }

}