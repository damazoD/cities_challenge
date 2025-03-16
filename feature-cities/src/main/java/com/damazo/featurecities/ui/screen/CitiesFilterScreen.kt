package com.damazo.featurecities.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
    var expanded by rememberSaveable { mutableStateOf(false) }
    val uiState by viewModel.citiesFilterUiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.searchSavedData()
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .semantics { isTraversalGroup = true })
        {
            when (uiState) {
                is ErrorData -> ErrorDataView()
                is Downloading -> DownloadProgressView()
                else -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        SearchBar(
                            modifier = Modifier
                                // .align(Alignment.TopCenter)
                                .semantics { traversalIndex = 0f },
                            inputField = {
                                SearchBarDefaults.InputField(
                                    query = textToSearch,
                                    onQueryChange = {
                                        textToSearch = it
                                        viewModel.filterCountries(it)
                                    },
                                    onSearch = {
                                        expanded = false
                                    },
                                    expanded = expanded,
                                    onExpandedChange = {
                                        expanded = it
                                    },
                                    placeholder = {
                                        Text(text = stringResource(R.string.filter_hint))
                                    },
                                    leadingIcon = {
                                        val image = if (expanded) {
                                            Icons.AutoMirrored.Filled.ArrowBack
                                        } else {
                                            Icons.Default.Search
                                        }
                                        Icon(
                                            modifier = Modifier.clickable {
                                                textToSearch = ""
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
                            expanded = expanded,
                            onExpandedChange = { expanded = it },
                        ) {
                            Column(
                                modifier = Modifier.verticalScroll(rememberScrollState())
                            ) {
                                repeat(4) {
                                    val resultText = "Suggestion $it"
                                    ListItem(
                                        headlineContent = { Text(resultText) },
                                        supportingContent = { Text("Additional info") },
                                        leadingContent = {
                                            Icon(
                                                Icons.Filled.Star,
                                                contentDescription = null
                                            )
                                        },
                                        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                                        modifier = Modifier
                                            .clickable {
                                                textToSearch = resultText
                                                expanded = false
                                            }
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp, vertical = 4.dp)
                                    )
                                }
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
        }
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun CitiesFilterScreenPreview() {
    CitiesFilterScreen { }
}