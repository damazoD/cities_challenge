package com.damazo.data.datasource

import com.damazo.data.model.CityEntity

interface CitiesStorage {
    fun saveAllCities(cities: List<CityEntity>)
    fun searchCities(prefix: String): List<CityEntity>
    fun saveFavouriteCity(id: String)
}