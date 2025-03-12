package com.damazo.featurecountry.model

sealed class CountriesUiState {
    data object Standby : CountriesUiState()
    data object Filtering : CountriesUiState()
    data object Downloading : CountriesUiState()
    data object EmptyData : CountriesUiState()
    data object ErrorData : CountriesUiState()
    data object DataFound : CountriesUiState()
    data class SuccessfulFilter(val countries: List<Country>) : CountriesUiState()
}