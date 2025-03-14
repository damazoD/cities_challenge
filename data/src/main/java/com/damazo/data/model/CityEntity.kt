package com.damazo.data.model

class CityEntity(
    val id: String,
    val name: String,
    val country: String,
    val coordinate: CoordinateEntity,
    val isFavourite: Boolean,
)