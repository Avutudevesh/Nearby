package com.dev.nearby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.dev.nearby.data.local.NearbyDatabase
import com.dev.nearby.data.network.NearbyNetworkDataSource
import com.dev.nearby.data.paging.NearbyRemoteMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalPagingApi::class)
internal class MainViewModel(
    private val nearbyDatabase: NearbyDatabase,
    private val nearbyNetworkDataSource: NearbyNetworkDataSource,
) : ViewModel() {

    private val _distanceFilterState = MutableStateFlow(0f)
    val distanceFilterState = _distanceFilterState

    @OptIn(ExperimentalCoroutinesApi::class)
    val pageData = _distanceFilterState.flatMapLatest { distance ->
        val range = if (distance == 0f) {
            MIN_DISTANCE
        } else {
            (distance * MAX_DISTANCE).toInt()
        }
        Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 10, initialLoadSize = 10),
            pagingSourceFactory = {
                nearbyDatabase.nearByLocalSource().pagingSource()
            },
            remoteMediator = NearbyRemoteMediator(nearbyNetworkDataSource, nearbyDatabase, range)
        ).flow
    }

    fun onDistanceFilterChange(value: Float) {
        _distanceFilterState.value = value
    }

    companion object {
        const val MIN_DISTANCE = 1
        const val MAX_DISTANCE = 100
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                MainViewModel(
                    nearbyDatabase = app.nearbyDatabase,
                    nearbyNetworkDataSource = app.nearbyNetworkDataSource
                )
            }
        }
    }

}