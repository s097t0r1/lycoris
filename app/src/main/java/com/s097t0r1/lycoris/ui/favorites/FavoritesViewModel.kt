package com.s097t0r1.lycoris.ui.favorites

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.s097t0r1.lycoris.data.Photo
import com.s097t0r1.lycoris.data.Success
import com.s097t0r1.lycoris.data.Error
import com.s097t0r1.lycoris.data.source.PhotoRepository
import kotlinx.coroutines.launch

class FavoritesViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _photos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>>
        get() = _photos

    private val _emptyResult = MutableLiveData<Boolean>(false)
    val emptyResult: LiveData<Boolean>
        get() = _emptyResult

    private val _errorLoadingData = MutableLiveData<Boolean>(false)
    val errorLoadingData: LiveData<Boolean>
        get() = _errorLoadingData

    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    init {
        getPhotos()
    }

    fun getPhotos() {
        viewModelScope.launch {
            _emptyResult.value = false
            _dataLoading.value = true
            val result = photoRepository.getPhotos(false)
            when(result) {
                is Success -> _photos.value = result.data
                is Error -> _emptyResult.value = true
            }
            _dataLoading.value = false
        }
    }


}