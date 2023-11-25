package com.dev.nearby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dev.nearby.data.NearByRepository
import com.dev.nearby.data.local.NearbyDatabase
import com.dev.nearby.data.local.VenueEntity
import com.dev.nearby.data.network.NearbyNetworkDataSource
import com.dev.nearby.data.paging.NearbyRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal class MainViewModel(
    private val nearbyDatabase: NearbyDatabase,
    private val nearbyNetworkDataSource: NearbyNetworkDataSource,
    private val nearByRepository: NearByRepository
) : ViewModel() {

    private val _distanceFilterState = MutableStateFlow(0f)
    val distanceFilterState = _distanceFilterState

    @OptIn(ExperimentalPagingApi::class)
    val nearByLocations: Flow<PagingData<VenueEntity>> = Pager(
        config = PagingConfig(pageSize = 10, prefetchDistance = 10, initialLoadSize = 10),
        pagingSourceFactory = {
            nearbyDatabase.nearByLocalSource().pagingSource()
        },
        remoteMediator = NearbyRemoteMediator(nearbyNetworkDataSource, nearbyDatabase, nearByRepository)
    ).flow


    fun onDistanceFilterChange(value: Float) {
        _distanceFilterState.value = value
        nearByRepository.distanceFilter = if (distanceFilterState.value == 0f) {
            MIN_DISTANCE
        } else {
            (distanceFilterState.value * MAX_DISTANCE).toInt()
        }
    }

    companion object {
        const val MIN_DISTANCE = 1
        const val MAX_DISTANCE = 100
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                MainViewModel(
                    nearbyDatabase = app.nearbyDatabase,
                    nearbyNetworkDataSource = app.nearbyNetworkDataSource,
                    nearByRepository = app.nearbyRepository
                )
            }
        }
    }

}