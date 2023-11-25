package com.dev.nearby.data

import com.dev.nearby.data.models.Venue
import kotlinx.coroutines.flow.Flow

internal interface NearbyRepository {

    fun fetchNearbyVenues(lat: Double, lng: Double): Flow<List<Venue>>

}