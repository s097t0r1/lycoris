package com.s097t0r1.lycoris.data

import com.s097t0r1.lycoris.data.source.local.DatabasePhoto

data class Photo (
        val id: String,
        val description: String,
        val imageUrl: String,
        var isFavorite: Boolean
)

fun Photo.toDatabaseModel(): DatabasePhoto {
    return DatabasePhoto(
        id = this.id,
        description = this.description,
        imageUrl = this.imageUrl
    )
}