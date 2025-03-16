package com.damazo.domain.model

class City(
    val id: Long,
    val name: String,
    val country: String,
    val coordinates: Coordinates?,
    val isFavourite: Boolean,
)