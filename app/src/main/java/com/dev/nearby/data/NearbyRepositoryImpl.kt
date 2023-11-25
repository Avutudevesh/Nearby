package com.dev.nearby.data

import com.dev.nearby.data.models.Venue
import com.dev.nearby.data.network.NearbyNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NearbyRepositoryImpl(
    private val networkDataSource: NearbyNetworkDataSource
) : NearbyRepository {

    override fun fetchNearbyVenues(lat: Double, lng: Double): Flow<List<Venue>> {
        return flow {
            val remoteVenues = networkDataSource.fetchNearbyVenues(lat, lng)
            val venues = remoteVenues.map {
                Venue(
                    it.name,
                    it.url,
                    it.address
                )
            }
            emit(venues)
        }
    }

}