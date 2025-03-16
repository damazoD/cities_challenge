package com.damazo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesRemote(
    @SerialName("lon")
    val longitude: Double?,
    @SerialName("lat")
    val latitude: Double?,
)