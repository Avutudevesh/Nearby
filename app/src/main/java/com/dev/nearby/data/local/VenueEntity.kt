package com.dev.nearby.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "venues"
)
data class VenueEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val url: String?,
    val address: String?,
    val pageNo: Int
)