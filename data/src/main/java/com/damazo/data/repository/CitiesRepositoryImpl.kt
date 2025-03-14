package com.damazo.data.repository

import com.damazo.data.datasource.CitiesNetwork
import com.damazo.data.datasource.CitiesStorage
import com.damazo.data.model.CityEntity
import com.damazo.data.model.CoordinateEntity
import com.damazo.domain.model.City
import com.damazo.domain.model.Coordinate
import com.damazo.domain.repository.CitiesRepository
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val citiesStorage: CitiesStorage,
    private val citiesNetwork: CitiesNetwork,
) : CitiesRepository {

    override fun downloadData() {
        val cities = citiesNetwork.getAllCities().map {
            CityEntity(
                id = it.id,
                name = it.name,
                country = it.country,
                coordinate = CoordinateEntity(it.coordinate.latitude, it.coordinate.longitude),
                isFavourite = it.isFavourite,
            )
        }
        citiesStorage.saveAllCities(cities)
    }

    override fun searchCities(prefix: String): List<City> {
        return citiesStorage.searchCities(prefix).map {
            City(
                id = it.id,
                name = it.name,
                country = it.country,
                coordinate = Coordinate(it.coordinate.latitude, it.coordinate.longitude),
                isFavourite = it.isFavourite,
            )
        }
    }

    override fun saveFavourite(cityId: String) {
        citiesStorage.saveFavouriteCity(cityId)
    }
}