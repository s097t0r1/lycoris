package com.s097t0r1.lycoris.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.s097t0r1.lycoris.data.Photo

@Entity(tableName = "photos")
data class DatabasePhoto (
        @PrimaryKey
        val id: String,
        val description: String,
        val imageUrl: String
)

fun DatabasePhoto.toDomainModel(): Photo {
        return Photo(
                id = this.id,
                description = this.description,
                imageUrl = this.imageUrl,
                isFavorite = true
        )
}

fun List<DatabasePhoto>.mapToDomainModel(): List<Photo> {
        return this.map {
                it.toDomainModel()
        }
}