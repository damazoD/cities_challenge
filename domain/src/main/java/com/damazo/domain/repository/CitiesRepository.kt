package com.damazo.domain.repository

import com.damazo.domain.model.City

interface CitiesRepository {
    fun downloadData()
    fun searchCities(prefix:String): List<City>
    fun saveFavourite(cityId: String)
}