package com.s097t0r1.lycoris.di

import android.content.Context
import com.s097t0r1.lycoris.data.source.local.DAO
import com.s097t0r1.lycoris.data.source.local.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabaseDAO(@ApplicationContext context: Context): DAO {
        return PhotoDatabase.getInstance(context).databaseDAO()
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSourceQualifier