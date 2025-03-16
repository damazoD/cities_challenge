package com.damazo.featurecountry.model

sealed class CitiesFilterUiState {
    data object Standby : CitiesFilterUiState()
    data object Downloading : CitiesFilterUiState()
    data object EmptyData : CitiesFilterUiState()
    data object ErrorData : CitiesFilterUiState()
    data class DataFound(val countries: List<City>) : CitiesFilterUiState()
    data class SuccessfulFilter(val countries: List<City>) : CitiesFilterUiState()
}