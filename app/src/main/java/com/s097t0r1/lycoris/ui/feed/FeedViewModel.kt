package com.s097t0r1.lycoris.ui.feed

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.s097t0r1.lycoris.data.Photo
import com.s097t0r1.lycoris.data.Success
import com.s097t0r1.lycoris.data.Error
import com.s097t0r1.lycoris.data.source.PhotoRepository
import com.s097t0r1.lycoris.data.source.remote.NetworkPhoto
import com.s097t0r1.lycoris.data.source.remote.UnsplashAPIService
import com.s097t0r1.lycoris.data.source.remote.mapToDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FeedViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    init {
        getPhotos()
    }

    fun getPhotos() {
        viewModelScope.launch {
            _dataLoading.value = true
            val result = photoRepository.getPhotos(true)

            when(result) {
                is Success -> _photos.value = result.data
                is Error -> _errorMessage.value = result.e.message ?: "Unknown error"
            }
        }

        _dataLoading.value = false
    }

    fun errorEventComplete() {
        _errorMessage.value = null
    }


}