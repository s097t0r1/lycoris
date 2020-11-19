package com.s097t0r1.lycoris.data.source.remote

import android.util.Log
import com.s097t0r1.lycoris.data.Error
import com.s097t0r1.lycoris.data.Result
import com.s097t0r1.lycoris.data.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val networkApi: UnsplashAPIService
) {
    suspend fun getPhoto(id: String): Result<NetworkPhoto> {
        return withContext(Dispatchers.IO) {
            try {
                val remotePhoto = networkApi.getPhoto(id)
                return@withContext Success(remotePhoto)
            } catch (e: Exception) {
                return@withContext Error(e)
            }
        }
    }

    suspend fun getPhotos(): Result<List<NetworkPhoto>> {
        return withContext(Dispatchers.IO) {
            try {
                val remotePhotos = networkApi.getPhotos()
                return@withContext Success(remotePhotos)
            } catch (e: Exception) {
                return@withContext Error(e)
            }
        }
    }
}