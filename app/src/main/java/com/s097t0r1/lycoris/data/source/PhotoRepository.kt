package com.s097t0r1.lycoris.data.source

import androidx.paging.*
import com.s097t0r1.lycoris.data.*
import com.s097t0r1.lycoris.data.source.local.DatabasePhoto
import com.s097t0r1.lycoris.data.source.local.mapToDomainModel
import com.s097t0r1.lycoris.data.source.local.toDomainModel
import com.s097t0r1.lycoris.data.source.remote.NetworkPhoto
import com.s097t0r1.lycoris.data.source.remote.RemotePhotoPagingSource
import com.s097t0r1.lycoris.data.source.remote.mapToDomainModel
import com.s097t0r1.lycoris.data.source.remote.toDomainModel
import com.s097t0r1.lycoris.di.LocalDataSourceQualifier
import com.s097t0r1.lycoris.di.RemoteDataSourceQualifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    @LocalDataSourceQualifier private val localDataSource: DefaultDataSource<DatabasePhoto>,
    @RemoteDataSourceQualifier private val remoteDataSource: DefaultDataSource<NetworkPhoto>,
    private val remotePhotoPagingSource: RemotePhotoPagingSource
) {

    suspend fun getPhoto(id: String): Result<Photo> {
        return withContext(Dispatchers.Default) {
            val localPhotoResult = localDataSource.getPhoto(id)

            if(localPhotoResult is Success && localPhotoResult.data != null) {
                return@withContext Success(localPhotoResult.data.toDomainModel())
            }

            val remotePhotoResult = remoteDataSource.getPhoto(id)

            if(remotePhotoResult is Success) {
                return@withContext Success(remotePhotoResult.data.toDomainModel())
            }

            return@withContext Error(Exception("Check internet connection"))

        }
    }

    suspend fun getPhotos(forceUpdate: Boolean): Result<List<Photo>> {
        return withContext(Dispatchers.Default) {
            if(forceUpdate) {
                val remoteResult = remoteDataSource.getPhotos()

                if(remoteResult is Success)
                    return@withContext Success(remoteResult.data.mapToDomainModel())

                return@withContext Error(Exception("Check internet connection"))
            }

            val localResult = localDataSource.getPhotos()

            if(localResult is Success && localResult.data.isNotEmpty())
                return@withContext Success(localResult.data.mapToDomainModel())

            return@withContext Error(Exception("Database is empty"))
        }
    }

    fun getPhotos(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { remotePhotoPagingSource }
        ).flow.map { remotePagingData ->
            remotePagingData.map { it.toDomainModel() }
        }

    suspend fun insertPhoto(photo: Photo) {
        withContext(Dispatchers.IO) {
            localDataSource.insertPhoto(photo.toDatabaseModel())
        }
    }

    suspend fun deletePhoto(photo: Photo) {
        withContext(Dispatchers.IO) {
            localDataSource.deletePhoto(photo.toDatabaseModel())
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}