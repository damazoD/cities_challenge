package com.damazo.featurecities.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
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

@Composable
fun MapView(
    modifier: Modifier,
    city: City,
    viewModel: CityDetailViewModel = hiltViewModel(),
    onCloseCallback: () -> Unit,
    onFavouriteCallback: (isFavourite: Boolean) -> Unit,
) {
    val coordinates = LatLng(city.coordinates!!.latitude, city.coordinates.longitude)
    val markerState = rememberMarkerState(position = coordinates)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(coordinates, 10f)
    }
    var isFavourite by remember { mutableStateOf(city.isFavourite) }

    Box(
        modifier.fillMaxSize()
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = markerState,
                title = city.displayName,
                snippet = city.displayCoordinates
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(15))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(horizontal = 10.dp, vertical = 5.dp),
            text = city.displayName
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(10.dp)
                .clip(RoundedCornerShape(15))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(2.dp),
            onClick = onCloseCallback
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(R.string.cities_download_button_description),
                tint = MaterialTheme.colorScheme.primary,
            )
        }

        IconButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(10.dp)
                .clip(RoundedCornerShape(15))
                .background(color = MaterialTheme.colorScheme.tertiaryContainer)
                .padding(2.dp),
            onClick = {
                isFavourite = !isFavourite
                viewModel.saveFavouriteCity(city.id, isFavourite)
                onFavouriteCallback(isFavourite)
            }
        ) {
            val favouriteIcon = R.drawable.heart.takeIf { isFavourite } ?: R.drawable.heart_outline
            Icon(
                imageVector = ImageVector.vectorResource(favouriteIcon),
                contentDescription = stringResource(R.string.maps_favourite_button_description),
                tint = Color.Red
            )
        }
    }
}
