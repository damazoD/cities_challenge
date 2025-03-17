package com.damazo.domain.repository

import com.damazo.domain.model.City

interface CitiesRepository {
    suspend fun downloadData(): List<City>
    suspend fun getSavedData(): List<City>
    suspend fun searchCities(prefix: String, onlyFavourites: Boolean): List<City>
    suspend fun saveFavourite(cityId: Long, isFavourite: Boolean)
}