package com.example.studyapp.ui.info.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.studyapp.GifSearcherApplication
import com.example.studyapp.MainActivity
import com.example.studyapp.R
import com.example.studyapp.databinding.InfoFragmentBinding
import com.example.studyapp.di.info.components.InfoComponent
import com.example.studyapp.ui.info.viewmodels.InfoViewModel
import javax.inject.Inject

class InfoFragment: Fragment() {
    @Inject
    lateinit var viewModel: InfoViewModel

    private lateinit var fragmentComponent: InfoComponent


    private var _binding: InfoFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = InfoFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Creation", "GifInfo OnCreate()")

        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val _activity = activity as MainActivity
        val _application = _activity.application as GifSearcherApplication

        fragmentComponent = _application.appComponent
            .gifInfoComponent()
            .create(this)

        fragmentComponent.inject(this)
        viewModel.applyComponent(fragmentComponent)
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
        _binding = InfoFragmentBinding.inflate(inflater, container, false)

        viewModel.currentGif.observe(viewLifecycleOwner) {
            Log.d("Net", "CurrentGif changed")
            if (it.gif.isNotEmpty()) {
                binding.gifView.setBackgroundResource(0)
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
                Glide.with(binding.gifView).load(it.gif).into(binding.gifView)
            }
        }

        viewModel.getGifInfo()

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