package com.s097t0r1.lycoris.ui.feed

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.s097t0r1.lycoris.data.source.remote.NetworkPhoto
import com.s097t0r1.lycoris.data.source.remote.UnsplashAPIService
import com.s097t0r1.lycoris.data.source.remote.mapToDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FeedViewModel @ViewModelInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val networkAPIService: UnsplashAPIService
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is feed Fragment"
    }
    val text: LiveData<String> = _text

    fun getPhotos() {
        viewModelScope.launch {
            Log.d("Retreiving data", getNetworkPhotos().mapToDomainModel().toString())
        }
    }

    suspend fun getNetworkPhotos(): List<NetworkPhoto> {
        return withContext(Dispatchers.IO) {
            return@withContext networkAPIService.getPhotos()
        }

    }


}