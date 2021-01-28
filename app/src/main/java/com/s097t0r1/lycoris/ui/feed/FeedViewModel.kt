package com.s097t0r1.lycoris.ui.feed

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.s097t0r1.lycoris.data.Photo
import com.s097t0r1.lycoris.data.source.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
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

    private var photoPagesStream: Flow<PagingData<Photo>>? = null

    init {
        getPhotos()
    }

    fun getPhotos(): Flow<PagingData<Photo>> {
        val newPhotoPagesStream = photoRepository.getPhotos().cachedIn(viewModelScope)

        photoPagesStream = newPhotoPagesStream

        return newPhotoPagesStream
    }


}