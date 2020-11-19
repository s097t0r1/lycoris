package com.s097t0r1.lycoris

import android.provider.ContactsContract
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.s097t0r1.lycoris.data.source.local.DatabasePhoto
import com.s097t0r1.lycoris.data.source.local.PhotoDatabase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoDatabaseTest {

    private lateinit var databaseTest: PhotoDatabase

    @Before
    fun initDB() {
        databaseTest = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, PhotoDatabase::class.java).build()
    }

    @After
    fun closeDB() {
        databaseTest.close()
    }

    @Test
    fun insertPhotos() {
        val testPhoto = DatabasePhoto("1", "Lorem ipsum", "https://picsum.photos/200")
        databaseTest.databaseDAO().insertPhoto(testPhoto)

        val databasePhotos = databaseTest.databaseDAO().getPhotos()

        assert(databasePhotos.isNotEmpty())
    }

    @Test
    fun getPhotos() {
        val testPhotoList: MutableList<DatabasePhoto> = ArrayList()

        testPhotoList.add(DatabasePhoto("1", "Lorem ipsum", "https://picsum.photos/200"))
        testPhotoList.add(DatabasePhoto("2", "Lorem ipsum", "https://picsum.photos/200"))
        testPhotoList.add(DatabasePhoto("3", "Lorem ipsum", "https://picsum.photos/200"))

        testPhotoList.forEach {
            databaseTest.databaseDAO().insertPhoto(it)
        }

        val testPhotoListFromDatabase = databaseTest.databaseDAO().getPhotos()

        assert(testPhotoListFromDatabase.containsAll(testPhotoList))
    }

    @Test
    fun getPhoto() {
        val testPhoto = DatabasePhoto("1", "Lorem ipsum", "https://picsum.photos/200")

        databaseTest.databaseDAO().insertPhoto(testPhoto)

        val testPhotoFromDatabase = databaseTest.databaseDAO().getPhoto("1")

        assert(testPhoto == testPhotoFromDatabase)
    }

    @Test
    fun getNotExistingPhoto() {
        val testPhoto = DatabasePhoto("1", "Lorem ipsum", "https://picsum.photos/200")

        databaseTest.databaseDAO().insertPhoto(testPhoto)

        val testPhotoFromDatabase = databaseTest.databaseDAO().getPhoto("2")

        assert(testPhotoFromDatabase == null)
    }

    @Test
    fun getNotExistingPhotos() {

        val testPhotoFromDatabase = databaseTest.databaseDAO().getPhotos()

        assert(testPhotoFromDatabase.isEmpty())
    }

}