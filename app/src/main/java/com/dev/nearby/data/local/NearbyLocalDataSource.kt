package com.dev.nearby.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
internal interface NearbyLocalDataSource {
    @Upsert
    suspend fun upsertAll(venues: List<VenueEntity>)

    @Query("SELECT * FROM venues")
    fun pagingSource(): PagingSource<Int, VenueEntity>

    @Query("DELETE FROM venues")
    suspend fun clearAll()
}