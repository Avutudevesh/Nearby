package com.dev.nearby.data.network

import com.dev.nearby.data.network.models.RemoteVenue

internal interface NearbyNetworkDataSource {

    suspend fun fetchNearbyVenues(lat: Double, lng: Double): List<RemoteVenue>

}