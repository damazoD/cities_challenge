package com.damazo.featurecountry.model

sealed class CountriesUiState {
    data object Filtering : CountriesUiState()
    data object Downloading : CountriesUiState()
    data class SuccessfulData(val countries: List<Country>) : CountriesUiState()
    data object EmptyData : CountriesUiState()
    data object ErrorData : CountriesUiState()
}