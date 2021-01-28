package com.s097t0r1.lycoris.di

import com.s097t0r1.lycoris.data.source.remote.AUTH_BODY
import com.s097t0r1.lycoris.data.source.remote.AUTH_HEADER
import com.s097t0r1.lycoris.data.source.remote.BASE_URL
import com.s097t0r1.lycoris.data.source.remote.UnsplashAPIService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(moshi: Moshi, httpClient: OkHttpClient): UnsplashAPIService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(UnsplashAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .addHeader(AUTH_HEADER, AUTH_BODY)
                .method(original.method(), original.body())
                .build()

            return@Interceptor chain.proceed(request)
        }).build()
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSourceQualifier