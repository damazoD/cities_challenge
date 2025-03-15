package com.damazo.data.datasource.remote

import com.damazo.data.model.CityRemote
import retrofit2.http.GET

interface CitiesGistService {

    companion object {
        const val PATH_PREFIX =
            "hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1"
    }

    @GET("${PATH_PREFIX}/cities.json")
    suspend fun getAllCities(): Result<List<CityRemote>>
}