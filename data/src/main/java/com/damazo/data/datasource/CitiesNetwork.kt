package com.damazo.data.datasource

import com.damazo.data.model.CityEntity

interface CitiesNetwork {
    fun getAllCities(): List<CityEntity>
}