package com.damazo.featurecities.model

sealed class CitiesFilterUiState {
    data object Standby : CitiesFilterUiState()
    data object Downloading : CitiesFilterUiState()
    data object Loading : CitiesFilterUiState()
    data object EmptyData : CitiesFilterUiState()
    data object ErrorData : CitiesFilterUiState()
    data class SuccessfulFilter(val countries: List<City>) : CitiesFilterUiState()
}