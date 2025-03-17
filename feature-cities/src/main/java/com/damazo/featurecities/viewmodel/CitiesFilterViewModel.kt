package com.damazo.featurecities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damazo.domain.usecase.DownloadCitiesUseCase
import com.damazo.domain.usecase.GetSavedCitiesUseCase
import com.damazo.domain.usecase.SearchCitiesUseCase
import com.damazo.featurecities.mapper.CityMapper
import com.damazo.featurecities.model.CitiesFilterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel() {

    private var _citiesFilterUiState: MutableStateFlow<CitiesFilterUiState> =
        MutableStateFlow(CitiesFilterUiState.Standby)
    val citiesFilterUiState: StateFlow<CitiesFilterUiState> = _citiesFilterUiState

    fun searchSavedData() {
        _citiesFilterUiState.value = CitiesFilterUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            val response = getSavedCitiesUseCase().map {
                mapper.mapToUiModel(it)
            }
            if (response.isEmpty()) {
                downloadData()
            } else {
                _citiesFilterUiState.value = CitiesFilterUiState.DataFound(response)
            }
        }
    }

    fun downloadData() {
        viewModelScope.launch(Dispatchers.IO) {
            _citiesFilterUiState.value = CitiesFilterUiState.Downloading
            val response = downloadCitiesUseCase().map {
                mapper.mapToUiModel(it)
            }
            if (response.isEmpty()) {
                _citiesFilterUiState.value = CitiesFilterUiState.ErrorData
            } else {
                _citiesFilterUiState.value = CitiesFilterUiState.DataFound(response)
            }
        }
    }

    fun filterCountries(text: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchCitiesUseCase(text).map {
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