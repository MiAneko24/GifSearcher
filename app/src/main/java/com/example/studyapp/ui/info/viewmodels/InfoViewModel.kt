package com.example.studyapp.ui.info.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studyapp.di.info.components.InfoComponent
import com.example.studyapp.logic.CurrentGifIdHolder
import com.example.studyapp.logic.GifLoader
import com.example.studyapp.ResultType
import com.example.studyapp.ui.models.GifDataUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InfoViewModel: ViewModel() {
    @Inject
    lateinit var gifLoader: GifLoader

    private val _currentGif: MutableLiveData<GifDataUI> =
        MutableLiveData(GifDataUI())
    val currentGif: LiveData<GifDataUI> = _currentGif


    private val _getInfoErrorMessage: MutableLiveData<String> = MutableLiveData("")
    val getInfoErrorMessage: LiveData<String> = _getInfoErrorMessage

    fun applyComponent(component: InfoComponent) {
        if (!this::gifLoader.isInitialized)
            component.inject(this)
    }

    fun getGifInfo() {
        val id = CurrentGifIdHolder.currentGifId

        val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
            _getInfoErrorMessage.postValue(throwable.message)
        }
        Log.d("Single gif", "Listener called successfully")
//        _getInfoErrorMessage.value = "Not yet implemented"
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler)
        {
            when (val gif = gifLoader.searchOne(id)) {
                is ResultType.Error -> _getInfoErrorMessage.postValue(gif.message)
                is ResultType.Ok -> {
                    Log.d("Net", "Before postValue")
                    _currentGif.postValue(
                        GifDataUI.fromModel(
                            gif.value
                        )
                    )
                    Log.d("Net", "After postValue")

                }
            }
        }
    }
}

