package com.damazo.featurecountry.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.damazo.featurecountry.model.CountriesUiState.*
import com.damazo.featurecountry.viewmodel.CountriesViewModel

@ExperimentalMaterial3Api
@Composable
fun CountriesScreen(
    modifier: Modifier,
    viewModel: CountriesViewModel = viewModel()
) {
    var text by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }

    val uiState by viewModel.countriesUiState.collectAsState()

    Box(
        modifier = Modifier
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
                                query = text,
                                onQueryChange = {
                                    text = it
                                },
                                onSearch = {
                                    expanded = false
                                },
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = it
                                },
                                placeholder = {
                                    Text("Hinted search text")
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Search, contentDescription = null)
                                },
                                trailingIcon = {
                                    Icon(Icons.Default.MoreVert, contentDescription = null)
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
                                            text = resultText
                                            expanded = false
                                        }
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                    when (uiState) {
                        is DataFound -> DataFoundView()
                        is EmptyData -> EmptyDataView()
                        is Filtering -> Unit //TODO(WIP)
                        is SuccessfulFilter ->
                            SuccessfulFilterView((uiState as SuccessfulFilter).countries) {
                                //TODO(Change to MapScreen)
                            }
                        else -> Unit
                    }
                }

            }
        }
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun CountriesScreenPreview() {
    CountriesScreen(Modifier)
}