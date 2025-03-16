package com.damazo.featurecities.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damazo.featurecities.R
import com.damazo.featurecities.model.City

@Composable
fun SuccessfulFilterView(
    countries: List<City>,
    onCountryPressed: (City) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .semantics { traversalIndex = 1f }
            .fillMaxSize()
            .background(color = Color.White),
    ) {
        items(count = countries.size) { index ->
            CountryItemView(countries[index], onCountryPressed)
            if (index < countries.lastIndex) {
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun CountryItemView(city: City, onCountryPressed: (City) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { onCountryPressed(city) }
        ) {
            Text(
                modifier = Modifier,
                text = city.displayName,
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier,
                text = city.displayCoordinates,
                fontSize = 14.sp
            )
        }
        val favouriteImage =
            R.drawable.heart.takeIf { city.isFavourite } ?: R.drawable.heart_outline
        Image(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(favouriteImage),
            colorFilter = ColorFilter.tint(color = Color.Red),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun SuccessfulFilterViewPreview() {
    SuccessfulFilterView(
        listOf(
            City(
                id = 123,
                displayName = "Mexico City, MX",
                isFavourite = true,
                displayCoordinates = "34.283333, 44.549999",
                coordinates = null,
            ),
            City(
                id = 123,
                displayName = "Axutla, MX",
                isFavourite = false,
                displayCoordinates = "34.283333, 44.549999",
                coordinates = null,
            ),
            City(
                id = 123,
                displayName = "El chinal, MX",
                isFavourite = true,
                displayCoordinates = "34.283333, 44.549999",
                coordinates = null,
            ),
        )
    ) { _ -> }
}
