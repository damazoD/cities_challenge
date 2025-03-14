package com.damazo.domain.model

class City(
    val id: String,
    val name: String,
    val country: String,
    val coordinate: Coordinate,
    val isFavourite: Boolean,
)