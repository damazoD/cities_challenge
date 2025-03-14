package com.damazo.featurecountry.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damazo.featurecountry.model.CitiesFilterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesFilterViewModel @Inject constructor(

) : ViewModel() {

    private var _citiesFilterUiState:MutableStateFlow<CitiesFilterUiState> = MutableStateFlow(CitiesFilterUiState.Standby)
    val citiesFilterUiState: StateFlow<CitiesFilterUiState> = _citiesFilterUiState

    init {
        searchSavedData()
    }

    private fun searchSavedData(){
        viewModelScope.launch {
            _citiesFilterUiState.value = CitiesFilterUiState.DataFound
        }
    }

    fun dataSourcePressed() {
        viewModelScope.launch {
            _citiesFilterUiState.value = CitiesFilterUiState.Downloading
            delay(3000)
            _citiesFilterUiState.value = CitiesFilterUiState.DataFound
        }
    }

    fun favouritePressed() {
        //SaveFavourite
    }

    fun filterCountries(text: String) {
        _citiesFilterUiState.value = CitiesFilterUiState.Filtering
        viewModelScope.launch {
            _citiesFilterUiState.value = CitiesFilterUiState.SuccessfulFilter(
                listOf(
                    //Country(),
                    //Country(),
                    )
            )
        }
    }
}