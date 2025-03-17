package com.damazo.featurecities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damazo.domain.usecase.DownloadCitiesUseCase
import com.damazo.domain.usecase.GetSavedCitiesUseCase
import com.damazo.domain.usecase.SearchCitiesUseCase
import com.damazo.featurecities.mapper.CityMapper
import com.damazo.featurecities.model.CitiesFilterUiState
import com.damazo.featurecities.model.City
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesFilterViewModel @Inject constructor(
    private val downloadCitiesUseCase: DownloadCitiesUseCase,
    private val getSavedCitiesUseCase: GetSavedCitiesUseCase,
    private val searchCitiesUseCase: SearchCitiesUseCase,
    private val mapper: CityMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private var _citiesFilterUiState: MutableStateFlow<CitiesFilterUiState> =
        MutableStateFlow(CitiesFilterUiState.Standby)
    val citiesFilterUiState: StateFlow<CitiesFilterUiState> = _citiesFilterUiState

    private var _allCities: MutableStateFlow<List<City>> = MutableStateFlow(emptyList())
    val allCities: StateFlow<List<City>> = _allCities

    fun searchSavedData() {
        _citiesFilterUiState.value = CitiesFilterUiState.Loading
        viewModelScope.launch(dispatcher) {
            val response = getSavedCitiesUseCase().map {
                mapper.mapToUiModel(it)
            }
            if (response.isEmpty()) {
                downloadData()
            } else {
                _allCities.value = response
                _citiesFilterUiState.value = CitiesFilterUiState.Standby
            }
        }
    }

    fun downloadData() {
        viewModelScope.launch(dispatcher) {
            _citiesFilterUiState.value = CitiesFilterUiState.Downloading
            val response = downloadCitiesUseCase().map {
                mapper.mapToUiModel(it)
            }
            if (response.isEmpty()) {
                _citiesFilterUiState.value = CitiesFilterUiState.ErrorData
            } else {
                _allCities.value = response
                _citiesFilterUiState.value = CitiesFilterUiState.Standby
            }
        }
    }

    fun filterCountries(text: String, onlyFavourites: Boolean) {
        viewModelScope.launch(dispatcher) {
            val response = searchCitiesUseCase(text, onlyFavourites).map {
                mapper.mapToUiModel(it)
            }
            if (response.isEmpty()) {
                _citiesFilterUiState.value = CitiesFilterUiState.EmptyData
            } else {
                _citiesFilterUiState.value = CitiesFilterUiState.SuccessfulFilter(response)
            }
        }
    }
}