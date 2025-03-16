package com.damazo.featurecities.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damazo.domain.usecase.SaveFavouriteCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityDetailViewModel @Inject constructor(
    private val saveFavouriteCityUseCase: SaveFavouriteCityUseCase
) : ViewModel() {

    fun saveFavouriteCity(id: Long, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            saveFavouriteCityUseCase(id, isFavourite)
        }
    }

}