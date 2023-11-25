package com.dev.nearby.data.network

import com.dev.nearby.data.network.models.NearbyRemoteResponse
import com.dev.nearby.data.network.retrofit.NearbyService

internal class NearbyNetworkDataSourceImpl(
    private val nearbyService: NearbyService
) : NearbyNetworkDataSource {
    override suspend fun fetchNearbyVenues(lat: Double, lng: Double, page: Int, distanceFilter: Int): NearbyRemoteResponse {
        return nearbyService.fetchNearByVenues(lat, lng, page, distanceFilter)
    }
}