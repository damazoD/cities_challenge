package com.damazo.domain.usecase

import com.damazo.domain.repository.CitiesRepository

class DownloadCitiesUseCase(
    private val repository: CitiesRepository,
) {

    operator fun invoke(){
        repository.downloadData()
    }
}