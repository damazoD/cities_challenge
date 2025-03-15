package com.damazo.domain.usecase

import com.damazo.domain.model.City
import com.damazo.domain.repository.CitiesRepository

class SearchCitiesUseCase(
    private val repository: CitiesRepository,
) {
    suspend operator fun invoke(prefix: String): List<City> {
        return repository.searchCities(prefix)
    }
}