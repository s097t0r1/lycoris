package com.s097t0r1.lycoris.ui.feed

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.s097t0r1.lycoris.data.Photo
import com.s097t0r1.lycoris.data.Success
import com.s097t0r1.lycoris.data.Error
import com.s097t0r1.lycoris.data.source.PhotoRepository
import kotlinx.coroutines.launch


class FeedViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    private val _errorLoadingData = MutableLiveData<Boolean>()
    val errorLoadingData: LiveData<Boolean>
        get() = _errorLoadingData

    private val _loadingData = MutableLiveData<Boolean>()
    val loadingData: LiveData<Boolean>
        get() = _loadingData

    init {
        getPhotos()
    }

    fun getPhotos() {
        viewModelScope.launch {
            _loadingData.value = true
            _errorLoadingData.value = false

            val result = photoRepository.getPhotos(true)

            when (result) {
                is Success -> _photos.value = result.data
                is Error -> _errorLoadingData.value = true
            }

            _loadingData.value = false
        }
    }

}