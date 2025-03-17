package com.damazo.data.repository

import com.damazo.data.datasource.local.CityDao
import com.damazo.data.datasource.remote.CitiesGistService
import com.damazo.data.mapper.CityMapper
import com.damazo.data.model.CityEntity
import com.damazo.data.model.CityRemote
import com.damazo.data.model.CoordinatesRemote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.verifyNoInteractions

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CitiesRepositoryImplTest {

    @Mock
    private lateinit var remoteDataSource: CitiesGistService

    @Mock
    private lateinit var localDataSource: CityDao

    private val mapper = CityMapper()

    private lateinit var repository: CitiesRepositoryImpl

    @Before
    fun setup() {
        repository = CitiesRepositoryImpl(remoteDataSource, localDataSource, mapper)
    }

    private val fakeRemoteCities = listOf(
        CityRemote(
            id = 123,
            name = "Mexico",
            country = "MX",
            coordinates = CoordinatesRemote(56.788, -78.123)
        ),
        CityRemote(
            id = 124,
            name = "Abasolo",
            country = "MX",
            coordinates = CoordinatesRemote(56.788, -78.123)
        ),
    )

    private val fakeLocalCities = fakeRemoteCities.mapNotNull {
        mapper.mapToEntityModel(it)
    }

    @Test
    fun `should save in localDataSource when the cities downloaded is not empty`() = runTest {
        val argumentCaptor = argumentCaptor<List<CityEntity>>()
        `when`(remoteDataSource.getAllCities())
            .thenReturn(fakeRemoteCities)

        repository.downloadData()

        verify(localDataSource).insertAll(argumentCaptor.capture())
        assertTrue(argumentCaptor.lastValue.size == 2)
    }

    @Test
    fun `should return sorted list when the cities downloaded is not empty`() = runTest {
        `when`(remoteDataSource.getAllCities())
            .thenReturn(fakeRemoteCities)

        val result = repository.downloadData()

        assertTrue(result.size == 2)
        assertTrue(result[0].name == "Abasolo")
        assertTrue(result[1].name == "Mexico")
    }

    @Test
    fun `should return empty list when the cities downloaded is empty`() = runTest {
        `when`(remoteDataSource.getAllCities())
            .thenReturn(emptyList())

        val result = repository.downloadData()

        assertTrue(result.isEmpty())
        verifyNoInteractions(localDataSource)
    }

    @Test
    fun `should return empty list when the download throws an exception`() = runTest {
        `when`(remoteDataSource.getAllCities())
            .thenThrow(RuntimeException("Error fetching cities"))

        val result = repository.downloadData()

        assertTrue(result.isEmpty())
        verifyNoInteractions(localDataSource)
    }

    @Test
    fun `should return the list returned for the localDataSource`() = runTest {
        `when`(localDataSource.getAllCities())
            .thenReturn(fakeLocalCities)

        val result = repository.getSavedData()

        assertTrue(result.size == 2)
        verify(localDataSource).getAllCities()
        verifyNoInteractions(remoteDataSource)
    }

    @Test
    fun `should call favourite search when the flag is true`() = runTest {
        `when`(localDataSource.searchFavouritesBy("prefix"))
            .thenReturn(fakeLocalCities)

        val result = repository.searchCities("prefix", true)

        assertTrue(result.size == 2)
        verify(localDataSource).searchFavouritesBy("prefix")
        verifyNoMoreInteractions(localDataSource)
    }

    @Test
    fun `should call normal search when the flag is false`() = runTest {
        `when`(localDataSource.searchBy("prefix"))
            .thenReturn(fakeLocalCities)

        val result = repository.searchCities("prefix", false)

        assertTrue(result.size == 2)
        verify(localDataSource).searchBy("prefix")
        verifyNoMoreInteractions(localDataSource)
    }

    @Test
    fun `should call update favourite from localDataSource`() = runTest {
        repository.saveFavourite(123, false)

        verify(localDataSource).updateFavouriteCity(123, false)
        verifyNoMoreInteractions(localDataSource)
    }
}