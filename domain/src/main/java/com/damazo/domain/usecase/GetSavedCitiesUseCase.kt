package com.damazo.domain.usecase

import com.damazo.domain.model.City
import com.damazo.domain.repository.CitiesRepository

class GetSavedCitiesUseCase(
    private val repository: CitiesRepository,
) {
    suspend operator fun invoke(): List<City>{
        return repository.getSavedData()
    }
}