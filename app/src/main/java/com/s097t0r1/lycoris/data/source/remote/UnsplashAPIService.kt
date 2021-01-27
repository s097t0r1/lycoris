package com.s097t0r1.lycoris.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.unsplash.com/"
const val ACCESS_KEY = "LRz23zhofhEVGQQmZzILVnBHb8ZpTGhYlQWkdRNY5OA"
const val AUTH_HEADER = "Authorization"
const val AUTH_BODY = "Client-ID $ACCESS_KEY"


interface UnsplashAPIService {

    @GET("photos")
    suspend fun getPhotos(@Query("page") page: Int): List<NetworkPhoto>

    @GET("photos/{id}")
    suspend fun getPhoto(@Path("id") photoId: String): NetworkPhoto

}