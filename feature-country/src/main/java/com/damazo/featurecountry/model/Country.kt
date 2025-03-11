package com.damazo.featurecountry.model

data class Country(
    val id: String,
    val name: String,
    val coordinate: Coordinate,
    val isFavourite: Boolean,
)
