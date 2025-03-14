package com.damazo.ualachallenge.di

import com.damazo.data.datasource.CitiesNetwork
import com.damazo.data.datasource.CitiesStorage
import com.damazo.data.datasource.local.CitiesDataBase
import com.damazo.data.datasource.remote.CitiesRetrofit
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

    @Binds
    abstract fun bindsCitiesStorage(citiesDataBase: CitiesDataBase): CitiesStorage

    @Binds
    abstract fun bindsCitiesNetwork(citiesRetrofit: CitiesRetrofit): CitiesNetwork
}