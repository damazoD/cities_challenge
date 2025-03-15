package com.damazo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CoordinatesRemote(
    @SerialName("lon")
    val longitude: Double?,
    @SerialName("lat")
    val latitude: Double?,
)