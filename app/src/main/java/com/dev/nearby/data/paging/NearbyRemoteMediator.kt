package com.dev.nearby.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dev.nearby.data.local.NearbyDatabase
import com.dev.nearby.data.local.VenueEntity
import com.dev.nearby.data.network.NearbyNetworkDataSource

@OptIn(ExperimentalPagingApi::class)
internal class NearbyRemoteMediator(
    private val networkDataSource: NearbyNetworkDataSource,
    private val nearbyDatabase: NearbyDatabase,
    private val range: Int
) : RemoteMediator<Int, VenueEntity>() {

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }

    private var nextKey: Int = 1
    override suspend fun load(loadType: LoadType, state: PagingState<Int, VenueEntity>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                1
            }

            LoadType.APPEND -> {
                nextKey
            }

            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        }
        try {
            val remoteResponse = networkDataSource.fetchNearbyVenues(12.971599, 77.594566, page, range)
            val endOfPaginationReached = remoteResponse.venues.isEmpty()
            nextKey = remoteResponse.meta.page + 1
            nearbyDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    nearbyDatabase.nearByLocalSource().clearAll()
                }
                val venueEntities = remoteResponse.venues.map {
                    VenueEntity(id = it.id, name = it.name, url = it.url, address = it.address, pageNo = remoteResponse.meta.page)
                }
                nearbyDatabase.nearByLocalSource().upsertAll(venueEntities)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: Exception) {
            return MediatorResult.Error(error)
        }
    }

}