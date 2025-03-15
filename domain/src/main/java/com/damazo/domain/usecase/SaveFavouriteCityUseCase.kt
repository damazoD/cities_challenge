package com.damazo.domain.usecase

import com.damazo.domain.repository.CitiesRepository

class SaveFavouriteCityUseCase(
    private val repository: CitiesRepository,
) {
    suspend operator fun invoke(id: Long, isFavourite: Boolean) {
        return repository.saveFavourite(id, isFavourite)
    }
}