package com.dev.nearby.data.network.retrofit

import com.dev.nearby.data.network.models.NearbyRemoteResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NearbyService {
    @GET("/venues")
    suspend fun fetchNearByVenues(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 10,
        @Query("client_id") clientId: String = CLIENT_ID
    ): NearbyRemoteResponse

    companion object {
        private const val BASE_URL = "https://api.seatgeek.com/2/"
        private const val CLIENT_ID = "Mzg0OTc0Njl8MTcwMDgxMTg5NC44MDk2NjY5"
        fun getNearbyService(): NearbyService {
            return Retrofit.Builder()
                .client(OkHttpClient.Builder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(NearbyService::class.java)
        }
    }
}