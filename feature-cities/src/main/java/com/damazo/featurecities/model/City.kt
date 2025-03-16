package com.damazo.featurecities.model

data class City(
    val id: Long,
    val displayName: String,
    val coordinates: Coordinates?,
    var isFavourite: Boolean,
) {
    val displayCoordinates = coordinates?.let { "${it.latitude}, ${it.longitude}" }.orEmpty()
}
