package com.s097t0r1.lycoris.data.source

import com.s097t0r1.lycoris.data.Result

interface DefaultDataSource<T> {

    suspend fun getPhoto(id: String): Result<T>

    suspend fun getPhotos(): Result<List<T>>

    suspend fun insertPhoto(photo: T)

    suspend fun deletePhoto(photo: T)
}