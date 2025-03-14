package com.damazo.ualachallenge.di

import com.damazo.domain.repository.CitiesRepository
import com.damazo.domain.usecase.DownloadCitiesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideDownloadCitiesUseCase(citiesRepository: CitiesRepository): DownloadCitiesUseCase {
        return DownloadCitiesUseCase(citiesRepository)
    }
}