package com.s097t0r1.lycoris.data.source.local

import androidx.room.*

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: DatabasePhoto)

    @Delete
    fun deletePhoto(photo: DatabasePhoto)

    @Query("SELECT * FROM photos WHERE id=:id")
    fun getPhoto(id: String): DatabasePhoto

    @Query("SELECT * FROM photos")
    fun getPhotos(): List<DatabasePhoto>
}