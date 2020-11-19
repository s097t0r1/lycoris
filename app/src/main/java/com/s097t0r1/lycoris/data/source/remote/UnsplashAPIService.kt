package com.s097t0r1.lycoris.data.source.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

const val BASE_URL = "https://api.unsplash.com/"
const val ACCESS_KEY = "LRz23zhofhEVGQQmZzILVnBHb8ZpTGhYlQWkdRNY5OA"
const val AUTH_HEADER = "Authorization: Client-ID $ACCESS_KEY"

val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

val unsplashAPIService = retrofit.create(UnsplashAPIService::class.java)

interface UnsplashAPIService {

    @Headers(AUTH_HEADER)
    @GET("photos?per_page=50")
    suspend fun getPhotos(): List<NetworkPhoto>

    @Headers(AUTH_HEADER)
    @GET("photos/{id}")
    suspend fun getPhoto(@Path("id") photoId: String): NetworkPhoto

}