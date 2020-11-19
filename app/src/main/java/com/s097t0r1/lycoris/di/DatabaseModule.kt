package com.s097t0r1.lycoris.di

import android.content.Context
import com.s097t0r1.lycoris.data.source.local.DAO
import com.s097t0r1.lycoris.data.source.local.PhotoDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    fun provideDatabaseDAO(@ApplicationContext context: Context): DAO {
        return PhotoDatabase.getInstance(context).databaseDAO()
    }
}