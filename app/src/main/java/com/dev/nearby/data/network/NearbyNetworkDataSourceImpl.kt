package com.dev.nearby.data.network

import com.dev.nearby.data.network.models.RemoteVenue
import com.dev.nearby.data.network.retrofit.NearbyService

internal class NearbyNetworkDataSourceImpl(
    private val nearbyService: NearbyService
) : NearbyNetworkDataSource {
    override suspend fun fetchNearbyVenues(lat: Double, lng: Double): List<RemoteVenue> {
        return nearbyService.fetchNearByVenues(lat, lng, 1).venues
    }
}