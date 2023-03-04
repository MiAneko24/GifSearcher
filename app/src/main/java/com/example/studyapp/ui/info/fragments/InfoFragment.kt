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
import com.example.studyapp.ui.callbacks.SetupToolbarCallback
import com.example.studyapp.databinding.InfoFragmentBinding
import com.example.studyapp.di.info.components.InfoComponent
import com.example.studyapp.ui.info.viewmodels.InfoViewModel
import javax.inject.Inject

class InfoFragment: Fragment() {
    @Inject
    lateinit var viewModel: InfoViewModel

    private lateinit var fragmentComponent: InfoComponent
    private lateinit var toolbarCallback: SetupToolbarCallback


    private var _binding: InfoFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = InfoFragment()
    }

    fun setCallback(setupToolbarCallback: SetupToolbarCallback) {
        toolbarCallback = setupToolbarCallback
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

    private fun checkCallback() {
        if (!this::toolbarCallback.isInitialized ) {
            val _activity = activity as MainActivity
            setCallback(_activity)
        }
    }

    private fun setupToolbar() {
        toolbarCallback.setTitle(getString(R.string.gif_info))
        toolbarCallback.setButtonUpVisibility(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        checkCallback()

        _binding = InfoFragmentBinding.inflate(inflater, container, false)
        setupToolbar()

        viewModel.getInfoErrorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty())
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }

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
                toolbarCallback.setTitle(it.gifMetadataUI.title)
                Glide.with(binding.gifView).load(it.gif).into(binding.gifView)
            }
        }

        viewModel.getGifInfo()

        return binding.root
    }

}