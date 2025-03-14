package com.damazo.featuremap.di

import com.damazo.domain.repository.CitiesRepository
import com.damazo.domain.usecase.SaveFavouriteCityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideDownloadCitiesUseCase(citiesRepository: CitiesRepository): SaveFavouriteCityUseCase {
        return SaveFavouriteCityUseCase(citiesRepository)
    }
}