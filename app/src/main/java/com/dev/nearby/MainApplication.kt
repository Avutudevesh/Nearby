package com.dev.nearby

import android.app.Application
import com.dev.nearby.data.NearbyRepository
import com.dev.nearby.data.NearbyRepositoryImpl
import com.dev.nearby.data.network.NearbyNetworkDataSourceImpl
import com.dev.nearby.data.network.retrofit.NearbyService

internal class MainApplication : Application() {

    lateinit var nearbyRepository: NearbyRepository
    override fun onCreate() {
        super.onCreate()
        nearbyRepository = NearbyRepositoryImpl(
            networkDataSource = NearbyNetworkDataSourceImpl(
                NearbyService.getNearbyService()
            )
        )
    }
}