package com.s097t0r1.lycoris.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class DatabasePhoto (
        @PrimaryKey
        val id: String,
        val description: String,
        val imageUrl: String
)