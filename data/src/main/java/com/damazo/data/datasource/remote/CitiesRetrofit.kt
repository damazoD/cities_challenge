package com.damazo.data.datasource.remote

import com.damazo.data.datasource.CitiesNetwork
import com.damazo.data.model.CityEntity
import javax.inject.Inject

class CitiesRetrofit @Inject constructor(
//TODO(DataBase instance)
) : CitiesNetwork {
    override fun getAllCities(): List<CityEntity> {
        //TODO("Not yet implemented")
        return emptyList()
    }

}