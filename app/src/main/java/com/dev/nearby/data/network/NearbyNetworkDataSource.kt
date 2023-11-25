package com.dev.nearby.data.network

import com.dev.nearby.data.network.models.NearbyRemoteResponse

internal interface NearbyNetworkDataSource {
    suspend fun fetchNearbyVenues(lat: Double, lng: Double, page: Int, distanceFilter: Int): NearbyRemoteResponse

}