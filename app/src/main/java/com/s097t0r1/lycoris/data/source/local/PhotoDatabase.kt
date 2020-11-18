package com.s097t0r1.lycoris.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabasePhoto::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun databaseDAO(): DAO

    companion object {

        @Volatile
        private var INSTANCE: PhotoDatabase? = null

        fun getInstance(context: Context): PhotoDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null)
                    instance = Room.databaseBuilder(context, PhotoDatabase::class.java, "photos-database")
                        .fallbackToDestructiveMigration()
                        .build()

                INSTANCE = instance

                return instance
            }
        }
    }
}