package com.s097t0r1.lycoris.data.source.remote

import com.s097t0r1.lycoris.data.Photo
import kotlinx.serialization.Serializable

@Serializable
data class NetworkPhoto(

    val id: String,

    val description: String = "",

    val urls : Map<String, String> = emptyMap(),
)

fun NetworkPhoto.toDomainModel(): Photo {
    return Photo(
        id = this.id,
        description = this.description,
        imageUrl = this.urls["regular"] ?: "https://picsum.photos/200/300",
        isFavorite = false
    )
}

fun List<NetworkPhoto>.mapToDomainModel(): List<Photo> {
    return this.map { it.toDomainModel() }
}
