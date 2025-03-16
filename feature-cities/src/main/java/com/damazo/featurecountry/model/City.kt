package com.damazo.featurecountry.model

data class City(
    val id: Long,
    val displayName: String,
    val displayCoordinates: String,
    val coordinates: Coordinates?,
    val isFavourite: Boolean,
)
