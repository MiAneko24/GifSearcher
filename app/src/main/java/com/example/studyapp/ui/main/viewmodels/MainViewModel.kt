package com.example.studyapp.ui.main.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.MainFragmentComponent
import com.example.studyapp.logic.GifLoader
import com.example.studyapp.network.models.ResultType
import com.example.studyapp.ui.main.models.GifModelUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel : ViewModel() {

    @Inject
    lateinit var gifLoader: GifLoader

    private val _gifs: MutableLiveData<List<GifModelUI>> =
        MutableLiveData(listOf())
    val gifs: LiveData<List<GifModelUI>> = _gifs


    private val _errorMessage: MutableLiveData<String> = MutableLiveData("")
    val errorMessage: LiveData<String> = _errorMessage

    fun applyComponent(component: MainFragmentComponent) {
        component.inject(this)
    }

    fun search(keyword: String) {
        viewModelScope.launch(Dispatchers.IO)
        {
                val searchResult = gifLoader.search(keyword)
                when (searchResult) {
                    is ResultType.Error -> _errorMessage.postValue(
                        searchResult.message
                    )
                    is ResultType.Ok -> _gifs.postValue(
                        searchResult.value
                            .map { GifModelUI.fromModel(it) }
                    )
                }
        }
    }


}