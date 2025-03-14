package com.damazo.featurecountry.model

data class City(
    val id: String,
    val displayName: String,
    val coordinate: Coordinate,
    val isFavourite: Boolean,
)
