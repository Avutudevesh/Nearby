package com.dev.nearby

import android.app.Application
import com.dev.nearby.data.NearByRepository
import com.dev.nearby.data.local.NearbyDatabase
import com.dev.nearby.data.network.NearbyNetworkDataSource
import com.dev.nearby.data.network.NearbyNetworkDataSourceImpl
import com.dev.nearby.data.network.retrofit.NearbyService

internal class MainApplication : Application() {

    lateinit var nearbyDatabase: NearbyDatabase
    lateinit var nearbyNetworkDataSource: NearbyNetworkDataSource
    lateinit var nearbyRepository: NearByRepository
    override fun onCreate() {
        super.onCreate()
        nearbyRepository = NearByRepository()
        nearbyDatabase = NearbyDatabase.get(this)
        nearbyNetworkDataSource = NearbyNetworkDataSourceImpl(NearbyService.getNearbyService())
    }
}