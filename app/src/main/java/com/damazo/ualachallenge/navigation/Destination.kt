package com.damazo.ualachallenge.navigation

import kotlinx.serialization.Serializable

sealed class Destination {
    @Serializable
    data object CitiesFilter : Destination()

    @Serializable
    data class CityMap(
        val id: Long,
        val displayName: String,
        val latitude: Double,
        val longitude: Double,
        var isFavourite: Boolean
    ) : Destination()
}