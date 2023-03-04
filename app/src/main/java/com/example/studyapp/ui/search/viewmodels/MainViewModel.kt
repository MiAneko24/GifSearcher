package com.example.studyapp.ui.search.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.di.search.components.SearchComponent
import com.example.studyapp.logic.CurrentGifIdHolder
import com.example.studyapp.logic.GifLoader
import com.example.studyapp.ResultType
import com.example.studyapp.ui.models.GifDataUI
import com.example.studyapp.ui.models.GifModelUI
import kotlinx.coroutines.CoroutineExceptionHandler
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

    fun applyComponent(component: SearchComponent) {
        if (!this::gifLoader.isInitialized)
            component.inject(this)
    }

    fun search(keyword: String) {
        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            _searchErrorMessage.postValue(throwable.message)
        }

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler)
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

    fun setGifPosition(position: Int): Boolean {
        val gifs = gifImages.value
        return if (gifs != null && position > 0 && position < gifs.size) {
            CurrentGifIdHolder.currentGifId = gifs[position].id
            true
        } else {
            false
        }
    }

}