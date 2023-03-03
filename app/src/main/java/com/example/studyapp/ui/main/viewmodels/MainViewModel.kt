package com.example.studyapp.ui.main.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.MainFragmentComponent
import com.example.studyapp.logic.GifLoader
import com.example.studyapp.network.models.ResultType
import com.example.studyapp.ui.main.models.GifDataUI
import com.example.studyapp.ui.main.models.GifModelUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : ViewModel() {

    @Inject
    lateinit var gifLoader: GifLoader

    private val _gifImages: MutableLiveData<List<GifModelUI>> =
        MutableLiveData(listOf())
    val gifImages: LiveData<List<GifModelUI>> = _gifImages

    private val _currentGif: MutableLiveData<GifDataUI> =
        MutableLiveData(GifDataUI())
    val currentGif: LiveData<GifDataUI> = _currentGif

    private val _searchErrorMessage: MutableLiveData<String> = MutableLiveData("")
    val searchErrorMessage: LiveData<String> = _searchErrorMessage

    private val _getInfoErrorMessage: MutableLiveData<String> = MutableLiveData("")
    val getInfoErrorMessage: LiveData<String> = _getInfoErrorMessage

    fun applyComponent(component: MainFragmentComponent) {
        if (!this::gifLoader.isInitialized)
            component.inject(this)
    }

    fun search(keyword: String) {
        viewModelScope.launch(Dispatchers.IO)
        {
                val searchResult = gifLoader.search(keyword)
                when (searchResult) {
                    is ResultType.Error -> _searchErrorMessage.postValue(
                        searchResult.message
                    )
                    is ResultType.Ok -> _gifImages.postValue(
                        searchResult.value
                            .map { GifModelUI.fromModel(it) }
                    )
                }
        }
    }

    fun getGifInfo(position: Int) {
        Log.d("Single gif", "Listener called successfully")
//        _getInfoErrorMessage.value = "Not yet implemented"
        viewModelScope.launch(Dispatchers.IO)
        {
            val gifs = gifImages.value
            if (gifs != null && position > 0 && position < gifs.size) {
                when (val gif = gifLoader.searchOne(gifs[position].id)) {
                    is ResultType.Error -> _getInfoErrorMessage.postValue(gif.message)
                    is ResultType.Ok -> _currentGif.postValue(
                        GifDataUI.fromModel(
                            gif.value
                        )
                    )
                }
            }
        }

    }


}