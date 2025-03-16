package com.damazo.featurecities.model

data class City(
    val id: Long,
    val displayName: String,
    val displayCoordinates: String,
    val coordinates: Coordinates?,
    var isFavourite: Boolean,
)
