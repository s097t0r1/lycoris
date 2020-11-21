package com.s097t0r1.lycoris.ui.favorites

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.s097t0r1.lycoris.data.Photo
import com.s097t0r1.lycoris.data.Success
import com.s097t0r1.lycoris.data.source.PhotoRepository
import kotlinx.coroutines.launch
import java.lang.Error
import java.lang.RuntimeException

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

    private val _loadingData = MutableLiveData<Boolean>(false)
    val loadingData: LiveData<Boolean>
        get() = _loadingData

    init {
        getPhotos()
    }

    fun getPhotos() {
        _errorLoadingData.value = false
        _emptyResult.value = false

        viewModelScope.launch {
            _loadingData.value = true
            val result = photoRepository.getPhotos(false)

            when(result) {
                is Success -> if(result.data.isEmpty()) _emptyResult.value = true else _photos.value = result.data
                is Error -> _errorLoadingData.value = true
            }

            _loadingData.value = false
        }
    }


}