package com.s097t0r1.lycoris.data.source.local

import com.s097t0r1.lycoris.data.Error
import com.s097t0r1.lycoris.data.Result
import com.s097t0r1.lycoris.data.Success
import com.s097t0r1.lycoris.data.source.DefaultDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
   private val dao: DAO
) : DefaultDataSource<DatabasePhoto> {

    override suspend fun insertPhoto(photo: DatabasePhoto) {
        withContext(Dispatchers.IO) {
                dao.insertPhoto(photo)
        }
    }

    override suspend fun getPhoto(id: String): Result<DatabasePhoto> {
        return withContext(Dispatchers.IO) {
            try {
                val databasePhoto = dao.getPhoto(id)

                if(databasePhoto != null)
                    return@withContext Success(databasePhoto)

                return@withContext Error(Exception("This photo doesn't exist"))
            } catch (e: Exception) {
                return@withContext Error(e)
            }
        }
    }

    override suspend fun getPhotos(): Result<List<DatabasePhoto>> {
        return withContext(Dispatchers.IO) {
            try {
                val listDatabasePhoto = dao.getPhotos()
                return@withContext Success(listDatabasePhoto)
            } catch (e: Exception) {
                return@withContext Error(e)
            }

        }
    }

    override suspend fun deletePhoto(photo: DatabasePhoto) {
        withContext(Dispatchers.IO) {
            dao.deletePhoto(photo)
        }
    }

}