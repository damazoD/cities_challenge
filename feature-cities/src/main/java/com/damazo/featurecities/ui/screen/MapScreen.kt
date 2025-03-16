package com.damazo.featurecities.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.damazo.featurecities.R
import com.damazo.featurecities.model.City
import com.damazo.featurecities.viewmodel.CityDetailViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@ExperimentalMaterial3Api
@Composable
fun CityMapScreen(
    viewModel: CityDetailViewModel = hiltViewModel(),
    city: City,
    onBackPressed: () -> Unit,
) {

    val singapore = LatLng(city.coordinates!!.latitude, city.coordinates.longitude)
    val singaporeMarkerState = rememberMarkerState(position = singapore)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                title = {
                    Text(city.displayName)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                city.isFavourite = !city.isFavourite
                viewModel.saveFavouriteCity(city.id, city.isFavourite)
            }) {
                val favouriteImage =
                    R.drawable.heart.takeIf { city.isFavourite } ?: R.drawable.heart_outline
                Icon(
                    imageVector = ImageVector.vectorResource(favouriteImage),
                    contentDescription = "Set favourite",
                    tint = Color.Red
                )
            }
        }
    ) { innerPadding ->
        GoogleMap(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = singaporeMarkerState,
                title = city.displayName,
                snippet = "Marker in Singapore"
            )
        }
    }

}

@ExperimentalMaterial3Api
@Preview
@Composable
fun PreviewMapScreen() {
    CityMapScreen(
        city = City(
            id = 123,
            displayName = "Mexico City, MX",
            isFavourite = true,
            displayCoordinates = "34.283333, 44.549999",
            coordinates = null,
        )
    ) { }
}

