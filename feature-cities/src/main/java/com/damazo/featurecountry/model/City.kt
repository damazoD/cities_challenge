package com.damazo.featurecountry.model

data class City(
    val id: String,
    val name: String,
    val country: String,
    val coordinate: Coordinate,
    val isFavourite: Boolean,
)
