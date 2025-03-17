package com.damazo.featurecities.viewmodel

import com.damazo.domain.usecase.SaveFavouriteCityUseCase
import com.damazo.featurecities.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.verifyNoMoreInteractions

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.StrictStubs::class)
class CityDetailViewModelTest {

    @Mock
    private lateinit var saveFavouriteCityUseCase: SaveFavouriteCityUseCase

    @InjectMocks
    private lateinit var viewModel: CityDetailViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `should call saveFavouriteCityUseCase when saveFavourite is call`() = runTest {
        viewModel.saveFavouriteCity(123, false)

        verify(saveFavouriteCityUseCase).invoke(123, false)
        verifyNoMoreInteractions(saveFavouriteCityUseCase)
    }
}