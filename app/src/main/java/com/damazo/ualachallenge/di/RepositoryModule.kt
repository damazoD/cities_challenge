package com.damazo.ualachallenge.di

import com.damazo.data.repository.CitiesRepositoryImpl
import com.damazo.domain.repository.CitiesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule() {

    @Binds
    abstract fun bindsCitiesRepository(citiesRepositoryImpl: CitiesRepositoryImpl): CitiesRepository
}