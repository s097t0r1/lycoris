package com.s097t0r1.lycoris.ui.details

import androidx.lifecycle.*
import com.s097t0r1.lycoris.data.Error
import com.s097t0r1.lycoris.data.Photo
import com.s097t0r1.lycoris.data.Success
import com.s097t0r1.lycoris.data.source.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val photoRepository: PhotoRepository
) :  ViewModel() {

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo>
        get() = _photo

    private val _errorLoadingData = MutableLiveData<Boolean>(false)
    val errorLoadingData: LiveData<Boolean>
        get() = _errorLoadingData

    private val _loadingData = MutableLiveData<Boolean>(false)
    val loadingData: LiveData<Boolean>
        get() = _loadingData

    fun getPhoto(id: String) {
        viewModelScope.launch {
            _errorLoadingData.value = false
            _loadingData.value = true

            val result = photoRepository.getPhoto(id)
            when(result) {
                is Success -> _photo.value = result.data
                is Error -> _errorLoadingData.value = true
            }

            _loadingData.value = false
        }
    }

    fun markFavorite(favorite: Boolean) {
        viewModelScope.launch {
            _photo.value?.let { photo ->
                if(favorite)
                    photoRepository.insertPhoto(photo)
                else
                    photoRepository.deletePhoto(photo)

                _photo.value!!.isFavorite = favorite
            }
        }
    }

}