package com.damazo.featurecities.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.damazo.featurecities.R
import com.damazo.featurecities.model.CitiesFilterUiState.Downloading
import com.damazo.featurecities.model.CitiesFilterUiState.EmptyData
import com.damazo.featurecities.model.CitiesFilterUiState.ErrorData
import com.damazo.featurecities.model.CitiesFilterUiState.Loading
import com.damazo.featurecities.model.CitiesFilterUiState.SuccessfulFilter
import com.damazo.featurecities.model.City
import com.damazo.featurecities.ui.view.DownloadProgressView
import com.damazo.featurecities.ui.view.EmptyDataView
import com.damazo.featurecities.ui.view.ErrorDataView
import com.damazo.featurecities.ui.view.ListCitiesView
import com.damazo.featurecities.ui.view.MapView
import com.damazo.featurecities.viewmodel.CitiesFilterViewModel

@ExperimentalMaterial3Api
@Composable
fun CitiesFilterScreen(
    viewModel: CitiesFilterViewModel = hiltViewModel(),
    onItemPressed: (City) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var selectedCity by remember { mutableStateOf<City?>(null) }
    var cityPosition by rememberSaveable { mutableIntStateOf(0) }
    var textToSearch by rememberSaveable { mutableStateOf("") }
    var isSearchBarExpanded by rememberSaveable { mutableStateOf(false) }
    var isWarningDialogVisible by rememberSaveable { mutableStateOf(false) }
    var favouritesOnlyFilter by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.citiesFilterUiState.collectAsState()
    val allCities by viewModel.allCities.collectAsState()

    LaunchedEffect(Unit) {
        if (allCities.isEmpty()) {
            viewModel.searchSavedData()
        }
    }

    fun customItemPressed(city: City, position: Int) {
        cityPosition = position
        if (isLandscape) {
            selectedCity = city
        } else {
            onItemPressed(city)
        }
    }
    val onExpandedChange: (Boolean) -> Unit = {
        isSearchBarExpanded = it
        if (isSearchBarExpanded.not()) {
            textToSearch = ""
        }
    }

    Scaffold(
        topBar = {
            TopBar(isSearchBarExpanded) {
                isWarningDialogVisible = true
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .semantics { isTraversalGroup = true }
        ) {
            when (uiState) {
                is ErrorData -> ErrorDataView()
                is Downloading -> DownloadProgressView()
                is Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .size(75.dp)
                        .align(Alignment.Center)
                )

                else -> {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            SearchBar(
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .semantics { traversalIndex = 0f },
                                colors = SearchBarColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                                    dividerColor = MaterialTheme.colorScheme.primary,
                                ),
                                inputField = {
                                    SearchBarDefaults.InputField(
                                        query = textToSearch,
                                        onQueryChange = {
                                            textToSearch = it
                                            viewModel.filterCountries(it, favouritesOnlyFilter)
                                        },
                                        onSearch = {
                                            isSearchBarExpanded = false
                                        },
                                        expanded = isSearchBarExpanded,
                                        onExpandedChange = onExpandedChange,
                                        placeholder = {
                                            Text(text = stringResource(R.string.filter_hint))
                                        },
                                        leadingIcon = {
                                            LeadingIcon(isSearchBarExpanded) {
                                                isSearchBarExpanded = false
                                                textToSearch = ""
                                            }
                                        },
                                        trailingIcon = {
                                            if (isSearchBarExpanded) {
                                                TrailingIcon(
                                                    textToSearch = textToSearch,
                                                    isChecked = favouritesOnlyFilter,
                                                    onIconClicked = {
                                                        textToSearch = ""
                                                    },
                                                    onCheckedChange = { checked ->
                                                        favouritesOnlyFilter = checked
                                                        viewModel.filterCountries(
                                                            textToSearch,
                                                            favouritesOnlyFilter
                                                        )
                                                    })
                                            }
                                        },
                                    )
                                },
                                expanded = isSearchBarExpanded,
                                onExpandedChange = onExpandedChange,
                            ) {
                                when (uiState) {
                                    is SuccessfulFilter -> {
                                        ListCitiesView(
                                            (uiState as SuccessfulFilter).countries,
                                            ::customItemPressed
                                        )
                                    }

                                    is EmptyData -> EmptyDataView()

                                    else -> Unit
                                }
                            }
                            if (allCities.isNotEmpty()) {
                                ListCitiesView(
                                    allCities,
                                    ::customItemPressed
                                )
                            }
                        }
                        if (isLandscape && selectedCity != null) {
                            MapView(
                                modifier = Modifier.weight(1f),
                                city = selectedCity!!,
                                onCloseCallback = {
                                    selectedCity = null
                                },
                                onFavouriteCallback = { isFavourite ->
                                    viewModel.updateCityItem(cityPosition, isFavourite)
                                }
                            )
                        }
                    }
                }
            }
        }
        if (isWarningDialogVisible) {
            DownloadWarningDialog(
                onConfirmation = {
                    isWarningDialogVisible = false
                    viewModel.downloadData()
                },
                onCancel = {
                    isWarningDialogVisible = false
                }
            )
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun TopBar(isSearchBarExpanded: Boolean, onRefreshClicked: () -> Unit) {
    if (!isSearchBarExpanded) {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name)) },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            actions = {
                IconButton(onClick = onRefreshClicked) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = stringResource(R.string.cities_download_button_description),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        )
    }
}

@Composable
private fun LeadingIcon(isSearchBarExpanded: Boolean, onIconClicked: () -> Unit) {
    val image = if (isSearchBarExpanded) {
        Icons.AutoMirrored.Filled.ArrowBack
    } else {
        Icons.Default.Search
    }
    Icon(
        modifier = Modifier.clickable(onClick = onIconClicked),
        imageVector = image,
        contentDescription = null
    )
}

@Composable
private fun TrailingIcon(
    textToSearch: String,
    isChecked: Boolean,
    onIconClicked: () -> Unit,
    onCheckedChange: (checked: Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (textToSearch.isNotEmpty()) {
            Icon(
                modifier = Modifier.clickable(onClick = onIconClicked),
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
            Spacer(Modifier.padding(10.dp))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Checkbox(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .size(16.dp),
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
            Text(
                modifier = Modifier.padding(4.dp),
                text = stringResource(R.string.filter_favourites),
                fontSize = 8.sp
            )
        }
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
private fun CitiesFilterScreenPreview() {
    CitiesFilterScreen { }
}