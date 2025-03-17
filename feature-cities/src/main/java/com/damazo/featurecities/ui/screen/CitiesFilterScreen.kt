package com.damazo.featurecities.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.damazo.featurecities.R
import com.damazo.featurecities.model.CitiesFilterUiState.DataFound
import com.damazo.featurecities.model.CitiesFilterUiState.Downloading
import com.damazo.featurecities.model.CitiesFilterUiState.EmptyData
import com.damazo.featurecities.model.CitiesFilterUiState.ErrorData
import com.damazo.featurecities.model.CitiesFilterUiState.Loading
import com.damazo.featurecities.model.CitiesFilterUiState.SuccessfulFilter
import com.damazo.featurecities.model.City
import com.damazo.featurecities.ui.view.DownloadProgressView
import com.damazo.featurecities.ui.view.EmptyDataView
import com.damazo.featurecities.ui.view.ErrorDataView
import com.damazo.featurecities.ui.view.SuccessfulFilterView
import com.damazo.featurecities.viewmodel.CitiesFilterViewModel

@ExperimentalMaterial3Api
@Composable
fun CitiesFilterScreen(
    viewModel: CitiesFilterViewModel = hiltViewModel(),
    onItemPressed: (City) -> Unit,
) {
    var textToSearch by rememberSaveable { mutableStateOf("") }
    var isSearchBarExpanded by rememberSaveable { mutableStateOf(false) }
    var isWarningDialogVisible by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.citiesFilterUiState.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.searchSavedData()
    }

    Scaffold(
        topBar = {
            if (!isSearchBarExpanded) {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.app_name))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    actions = {
                        IconButton(onClick = {
                            isWarningDialogVisible = true
                        }) {
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
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .semantics { isTraversalGroup = true }
                .background(Color.Transparent)
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
                    Column(
                        modifier = Modifier.fillMaxSize()
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
                                        viewModel.filterCountries(it)
                                    },
                                    onSearch = {
                                        isSearchBarExpanded = false
                                    },
                                    expanded = isSearchBarExpanded,
                                    onExpandedChange = {
                                        isSearchBarExpanded = it
                                    },
                                    placeholder = {
                                        Text(text = stringResource(R.string.filter_hint))
                                    },
                                    leadingIcon = {
                                        val image = if (isSearchBarExpanded) {
                                            Icons.AutoMirrored.Filled.ArrowBack
                                        } else {
                                            Icons.Default.Search
                                        }
                                        Icon(
                                            modifier = Modifier.clickable {
                                                isSearchBarExpanded = false
                                            },
                                            imageVector = image,
                                            contentDescription = null
                                        )
                                    },
                                    trailingIcon = {
                                        Icon(
                                            modifier = Modifier.clickable {
                                                textToSearch = ""
                                            },
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = null
                                        )
                                    },
                                )
                            },
                            expanded = isSearchBarExpanded,
                            onExpandedChange = { isSearchBarExpanded = it },
                        ) {

                            (uiState as? SuccessfulFilter)?.countries?.let { data ->
                                SuccessfulFilterView(data, onItemPressed)
                            }


                        }
                        when (uiState) {
                            is DataFound -> {
                                val data = (uiState as DataFound).countries
                                SuccessfulFilterView(data, onItemPressed)
                            }

                            is EmptyData -> EmptyDataView()
                            is SuccessfulFilter -> {
                                SuccessfulFilterView(
                                    (uiState as SuccessfulFilter).countries,
                                    onItemPressed
                                )
                            }

                            else -> Unit
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
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun CitiesFilterScreenPreview() {
    CitiesFilterScreen { }
}