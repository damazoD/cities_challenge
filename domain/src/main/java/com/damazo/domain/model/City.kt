package com.damazo.domain.model

class City(
    val id: Long,
    val name: String,
    val country: String,
    val coordinate: Coordinates?,
    val isFavourite: Boolean,
)