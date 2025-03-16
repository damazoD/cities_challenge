package com.damazo.data.repository

import com.damazo.data.datasource.local.CityDao
import com.damazo.data.datasource.remote.CitiesGistService
import com.damazo.data.mapper.CityMapper
import com.damazo.domain.model.City
import com.damazo.domain.repository.CitiesRepository
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val remoteDataSource: CitiesGistService,
    private val localDataSource: CityDao,
    private val mapper: CityMapper,
) : CitiesRepository {

    override suspend fun downloadData(): List<City> {
        return try {
            remoteDataSource.getAllCities()
                .mapNotNull(mapper::mapToEntityModel)
                .also { cities ->
                    if (cities.isNotEmpty()) {
                        localDataSource.insertAll(cities)
                    }
                }
                .map(mapper::mapToDomainModel)
                .sortedWith(compareBy({ it.name.lowercase() }, { it.country.lowercase() }))
        } catch (exception: Exception) {
            emptyList()
        }
    }

    override suspend fun getSavedData() = localDataSource.getAllCities().map {
        mapper.mapToDomainModel(it)
    }

    override suspend fun searchCities(prefix: String) = localDataSource.searchBy(prefix).map {
        mapper.mapToDomainModel(it)
    }

    override suspend fun saveFavourite(cityId: Long, isFavourite: Boolean) {
        localDataSource.updateFavouriteCity(cityId, isFavourite)
    }
}