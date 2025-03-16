package com.damazo.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityRemote(
    @SerialName("_id")
    val id: Long?,
    @SerialName("name")
    val name: String?,
    @SerialName("country")
    val country: String?,
    @SerialName("coord")
    val coordinates: CoordinatesRemote?,
)