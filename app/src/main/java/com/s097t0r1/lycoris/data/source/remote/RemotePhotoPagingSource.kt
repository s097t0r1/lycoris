package com.s097t0r1.lycoris.data.source.remote

import androidx.paging.PagingSource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RemotePhotoPagingSource @Inject constructor(
    private val apiService: UnsplashAPIService
) : PagingSource<Int, NetworkPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkPhoto> {
        try {
            val currentKey = params.key ?: 1
            val data = apiService.getPhotos(currentKey)

            return LoadResult.Page(
                data = data,
                prevKey = if(currentKey == 1) null else currentKey - 1,
                nextKey = if(data.isEmpty()) null else currentKey + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

}