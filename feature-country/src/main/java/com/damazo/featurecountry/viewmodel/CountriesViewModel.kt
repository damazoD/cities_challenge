package com.damazo.featurecountry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damazo.featurecountry.model.CountriesUiState
import com.damazo.featurecountry.model.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(

) : ViewModel() {

    private var _countriesUiState:MutableStateFlow<CountriesUiState> = MutableStateFlow(CountriesUiState.Standby)
    val countriesUiState: StateFlow<CountriesUiState> = _countriesUiState

    init {
        searchSavedData()
    }

    private fun searchSavedData(){
        viewModelScope.launch {
            _countriesUiState.value = CountriesUiState.DataFound
        }
    }

    fun dataSourcePressed() {
        viewModelScope.launch {
            _countriesUiState.value = CountriesUiState.Downloading
        }
    }

    fun favouritePressed() {
        //SaveFavourite
    }

    fun filterCountries(text: String) {
        _countriesUiState.value = CountriesUiState.Filtering
        viewModelScope.launch {
            _countriesUiState.value = CountriesUiState.SuccessfulFilter(
                listOf(
                    //Country(),
                    //Country(),
                    )
            )
        }
    }
}