package com.example.studyapp.ui.search.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.ResultType
import com.example.studyapp.di.search.components.SearchComponent
import com.example.studyapp.logic.CurrentGifIdHolder
import com.example.studyapp.logic.GifLoader
import com.example.studyapp.ui.models.GifModelUI
import com.example.studyapp.ui.models.SearchRequestUI
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

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _searchErrorMessage: MutableLiveData<String> = MutableLiveData("")
    val searchErrorMessage: LiveData<String> = _searchErrorMessage

    private val _searchKeywords: MutableLiveData<String> = MutableLiveData("")
    val searchKeywords: LiveData<String> = _searchKeywords


    fun applyComponent(component: SearchComponent) {
        if (!this::gifLoader.isInitialized)
            component.inject(this)
    }

    fun search(searchRequestUI: SearchRequestUI) {
        if (_isLoading.value == true) {
            _searchErrorMessage.value = "Previous request has not been processed yet"
            return
        }

        if (_searchKeywords.value != searchRequestUI.keywords) {
            _searchKeywords.value = searchRequestUI.keywords
        } else {
            _isLoading.value = true
        }

        val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
            _searchErrorMessage.postValue(throwable.message)
        }

        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler)
        {
            val loading = _isLoading.value

            val searchResult = gifLoader.search(searchRequestUI.toModel())
            when (searchResult) {
                is ResultType.Error -> _searchErrorMessage.postValue(
                    searchResult.message
                )
                is ResultType.Ok -> {
                    val oldList = if (loading != null && loading) {
                        _gifImages.value ?: listOf()
                    } else {
                        listOf()
                    }

                    _gifImages.postValue( oldList +
                        searchResult.value
                            .map { GifModelUI.fromModel(it) }
                    )
                }
            }
            _isLoading.postValue(false)
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