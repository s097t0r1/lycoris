package com.s097t0r1.lycoris.di

import com.s097t0r1.lycoris.data.source.DefaultDataSource
import com.s097t0r1.lycoris.data.source.local.DatabasePhoto
import com.s097t0r1.lycoris.data.source.local.LocalDataSource
import com.s097t0r1.lycoris.data.source.remote.NetworkPhoto
import com.s097t0r1.lycoris.data.source.remote.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class DataSourcesModule {

    @LocalDataSourceQualifier
    @Binds
    abstract fun bindLocalDataSource(dataSource: LocalDataSource): DefaultDataSource<DatabasePhoto>

    @RemoteDataSourceQualifier
    @Binds
    abstract fun bindRemoteDataSource(dataSource: RemoteDataSource): DefaultDataSource<NetworkPhoto>

}