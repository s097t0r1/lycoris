package com.s097t0r1.lycoris.ui.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.s097t0r1.lycoris.data.Error
import com.s097t0r1.lycoris.data.Photo
import com.s097t0r1.lycoris.data.Success
import com.s097t0r1.lycoris.data.source.PhotoRepository
import kotlinx.coroutines.launch


class DetailsViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository
) :  ViewModel() {

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo>
        get() = _photo

    private val _errorLoadingData = MutableLiveData<Boolean>(false)
    val errorLoadingData: LiveData<Boolean>
        get() = _errorLoadingData

    private val _errorMarkingFavorite = MutableLiveData<Boolean>(false)
    val errorMarkingFavorite: LiveData<Boolean>
        get() = _errorMarkingFavorite

    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean>
        get() = _dataLoading

    fun getPhoto(id: String) {
        viewModelScope.launch {
            _dataLoading.value = true
            val result = photoRepository.getPhoto(id)

            when(result) {
                is Success -> _photo.value = result.data
                is Error -> _errorLoadingData.value = true
            }

            _dataLoading.value = false
        }


    }

    fun markFavorite(favorite: Boolean) {
        viewModelScope.launch {

            if(photo.value == null) {
                _errorMarkingFavorite.value = true
                return@launch
            }

            if(favorite)
                photoRepository.insertPhoto(_photo.value!!)
            else
                photoRepository.deletePhoto(_photo.value!!)

            _photo.value!!.isFavorite = favorite
        }
    }


    fun errorEventComplete() {
        _errorLoadingData.value = false
    }

    fun errorMarkingFavoriteEventComplete() {
        _errorMarkingFavorite.value = false
    }
}