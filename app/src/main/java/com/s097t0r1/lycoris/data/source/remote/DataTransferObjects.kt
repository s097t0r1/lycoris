package com.s097t0r1.lycoris.data.source.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPhoto(
    val id: String,
    @Json(name = "created_at")
    val createdAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Transient
    @Json(name="promoted_at")
    val promotedAt: String = "",
    @Transient
    val width: Int = 0,
    @Transient
    val height: Int = 0,
    @Transient
    val color: String = "",
    @Transient
    @Json(name = "blur_hash")
    val blurHash: String = "",
    val description: String? = "",
    @Transient
    @Json(name = "alt_description")
    val altDescription : String = "",
    val urls : Map<String, String> = emptyMap(),
    @Transient
    val links : Map<String, String> = emptyMap(),
    @Transient
    val categories: List<String> = emptyList(),
    @Transient
    val likes : Int = 0,
    @Transient
    @Json(name = "liked_by_user")
    val likedByUser: Boolean = false,
    @Transient
    @Json(name = "current_user_collections")
    val currentUserCollections : List<String> = emptyList(),
    @Transient
    val sponsorship : Sponsorship? = null,
    @Transient
    val user : User? = null
)

@JsonClass(generateAdapter = true)
data class Sponsorship (

    @Json(name = "impression_urls")
    val impressionUrls : List<String>,
    val tagline : String,
    @Json(name = "tagline_url")
    val taglineUrl : String,
    val sponsor : User
)

@JsonClass(generateAdapter = true)
data class User (
    val id: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    val username: String,
    val name: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "twitter_username")
    val twitterUsername: String,
    @Json(name = "portfolio_url")
    val portfolioUrl: String,
    val bio: String,
    val location: String,
    val links: Map<String, String>,
    @Json(name = "profile_image")
    val profileImage: Map<String, String>,
    @Json(name = "instagram_username")
    val instagramUsername: String,
    @Json(name = "total_collections")
    val totalCollections: Int,
    @Json(name = "total_likes")
    val totalLikes: Int,
    @Json(name = "total_photos")
    val totalPhotos: Int,
    @Json(name = "accepted_tos")
    val acceptedTos: Boolean
)
