package com.s097t0r1.lycoris.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.s097t0r1.lycoris.data.source.remote.AUTH_BODY
import com.s097t0r1.lycoris.data.source.remote.AUTH_HEADER
import com.s097t0r1.lycoris.data.source.remote.BASE_URL
import com.s097t0r1.lycoris.data.source.remote.UnsplashAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @ExperimentalSerializationApi
    @Singleton
    @Provides
    fun provideApiService(httpClient: OkHttpClient): UnsplashAPIService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(Json{ignoreUnknownKeys = true; coerceInputValues = true}.asConverterFactory(contentType))
            .build()
            .create(UnsplashAPIService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                .addHeader(AUTH_HEADER, AUTH_BODY)
                .method(original.method, original.body)
                .build()

            return@Interceptor chain.proceed(request)
        }).build()
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSourceQualifier