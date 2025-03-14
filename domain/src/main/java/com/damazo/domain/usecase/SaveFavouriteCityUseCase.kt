package com.damazo.domain.usecase

import com.damazo.domain.repository.CitiesRepository

class SaveFavouriteCityUseCase(
    private val repository: CitiesRepository,
) {

    operator fun invoke(id: String) {
        return repository.saveFavourite(cityId = id)
    }
}