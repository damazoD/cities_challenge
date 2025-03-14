package com.damazo.data.datasource.local

import com.damazo.data.datasource.CitiesStorage
import com.damazo.data.model.CityEntity
import com.damazo.data.model.CoordinateEntity
import javax.inject.Inject

class CitiesDataBase @Inject constructor(
    //TODO(DataBase instance)
) : CitiesStorage {

    override fun saveAllCities(cities: List<CityEntity>) {
        //TODO(WIP)
    }

    override fun searchCities(prefix: String): List<CityEntity> {
        return getCities().filter { it.name.startsWith(prefix, ignoreCase = false) }
    }

    override fun saveFavouriteCity(id: String) {
        //TODO(WIP)
    }

    private fun getCities() = listOf(
        CityEntity(
            "123",
            name = "Mexico City",
            country = "MX",
            isFavourite = true,
            coordinate = CoordinateEntity(latitude = 34.283333, longitude = 44.549999)
        ),
        CityEntity(
            "123",
            name = "Axutla",
            country = "MX",
            isFavourite = false,
            coordinate = CoordinateEntity(latitude = 34.283333, longitude = 44.549999)
        ),
        CityEntity(
            "123",
            name = "El chinal",
            country = "MX",
            isFavourite = true,
            coordinate = CoordinateEntity(latitude = 34.283333, longitude = 44.549999)
        ),
    )

}