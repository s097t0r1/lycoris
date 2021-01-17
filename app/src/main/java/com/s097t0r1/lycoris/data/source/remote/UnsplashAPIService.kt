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
const val AUTH_HEADER = "Authorization"
const val AUTH_BODY = "Client-ID $ACCESS_KEY"


interface UnsplashAPIService {

    @GET("photos?per_page=50")
    suspend fun getPhotos(): List<NetworkPhoto>

    @GET("photos/{id}")
    suspend fun getPhoto(@Path("id") photoId: String): NetworkPhoto

}