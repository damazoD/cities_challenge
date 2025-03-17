package com.damazo.featurecities.viewmodel

import com.damazo.domain.model.City
import com.damazo.domain.model.Coordinates
import com.damazo.domain.usecase.DownloadCitiesUseCase
import com.damazo.domain.usecase.GetSavedCitiesUseCase
import com.damazo.domain.usecase.SearchCitiesUseCase
import com.damazo.featurecities.MainDispatcherRule
import com.damazo.featurecities.mapper.CityMapper
import com.damazo.featurecities.model.CitiesFilterUiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CitiesFilterViewModelTestTest {

    @Mock
    private lateinit var downloadCitiesUseCase: DownloadCitiesUseCase

    @Mock
    private lateinit var getSavedCitiesUseCase: GetSavedCitiesUseCase

    @Mock
    private lateinit var searchCitiesUseCase: SearchCitiesUseCase

    private val mapper = CityMapper()

    private lateinit var viewModel: CitiesFilterViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fakeCities = listOf(
        City(
            id = 123,
            name = "Mexico",
            country = "MX",
            coordinates = Coordinates(84.987, -98.45),
            isFavourite = true
        ),
        City(
            id = 123,
            name = "Acambaro",
            country = "MX",
            coordinates = Coordinates(20.987, -100.45),
            isFavourite = true
        ),
    )

    @Before
    fun setup() {
        viewModel = CitiesFilterViewModel(
            downloadCitiesUseCase,
            getSavedCitiesUseCase,
            searchCitiesUseCase,
            mapper,
            mainDispatcherRule.testDispatcher
        )
    }

    @Test
    fun `should return local data when there are saved values`() = runTest {
        whenever(getSavedCitiesUseCase())
            .thenReturn(fakeCities)

        viewModel.searchSavedData()

        verify(getSavedCitiesUseCase).invoke()
        verifyNoMoreInteractions(getSavedCitiesUseCase)
        assertEquals(CitiesFilterUiState.Standby, viewModel.citiesFilterUiState.value)
        assertTrue(viewModel.allCities.value.size == 2)
    }

    @Test
    fun `should return remote data when there are not saved values`() = runTest {
        whenever(getSavedCitiesUseCase())
            .thenReturn(emptyList())
        whenever(downloadCitiesUseCase())
            .thenReturn(fakeCities)

        viewModel.searchSavedData()

        verify(getSavedCitiesUseCase).invoke()
        verifyNoMoreInteractions(getSavedCitiesUseCase)
        assertEquals(CitiesFilterUiState.Standby, viewModel.citiesFilterUiState.value)
        assertTrue(viewModel.allCities.value.size == 2)
    }

    @Test
    fun `should return ErrorData uiState when no remote data is obtained`() = runTest {
        whenever(downloadCitiesUseCase())
            .thenReturn(emptyList())

        viewModel.downloadData()

        assertEquals(CitiesFilterUiState.ErrorData, viewModel.citiesFilterUiState.value)
        assertTrue(viewModel.allCities.value.isEmpty())
    }

    @Test
    fun `should return remote data when the request is successful`() = runTest {
        whenever(downloadCitiesUseCase())
            .thenReturn(fakeCities)

        viewModel.downloadData()

        assertEquals(CitiesFilterUiState.Standby, viewModel.citiesFilterUiState.value)
        assertTrue(viewModel.allCities.value.size == 2)
    }

    @Test
    fun `should return SuccessfulFilter with the cities when the filter is successful`() = runTest {
        whenever(searchCitiesUseCase("prefix", true))
            .thenReturn(fakeCities)

        viewModel.filterCountries("prefix", true)

        val uiState = viewModel.citiesFilterUiState.value as? CitiesFilterUiState.SuccessfulFilter
        assertNotNull(uiState)
        assertTrue(uiState?.countries?.size == 2)
    }

    @Test
    fun `should return EmptyData UiState when the filter is wrong`() = runTest {
        whenever(searchCitiesUseCase("prefix", true))
            .thenReturn(emptyList())

        viewModel.filterCountries("prefix", true)

        assertEquals(CitiesFilterUiState.EmptyData, viewModel.citiesFilterUiState.value)
    }
}