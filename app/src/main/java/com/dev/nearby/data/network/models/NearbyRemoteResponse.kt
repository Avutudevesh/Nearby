package com.dev.nearby.data.network.models

import com.google.gson.annotations.SerializedName


data class NearbyRemoteResponse(
    @SerializedName("venues")
    val venues: List<RemoteVenue>,
    @SerializedName("meta")
    val meta: Meta
)

data class RemoteVenue(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String?,
    @SerializedName("extended_address")
    val address: String?
)

data class Meta(
    val page: Int
)