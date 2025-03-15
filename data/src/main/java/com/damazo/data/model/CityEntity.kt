package com.damazo.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "city",
    indices = [
        Index(value = ["name", "country"])
    ]
)
class CityEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "country")
    val country: String,
    @Embedded(prefix = "coord_")
    val coordinates: CoordinatesEntity?,
    @ColumnInfo(name = "isFavourite")
    val isFavourite: Boolean,
)