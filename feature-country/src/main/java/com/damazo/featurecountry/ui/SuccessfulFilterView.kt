package com.damazo.featurecountry.ui

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damazo.featurecountry.R
import com.damazo.featurecountry.model.Coordinate
import com.damazo.featurecountry.model.Country

@Composable
fun SuccessfulFilterView(
    countries: List<Country>,
    onCountryPressed: (Country) -> Unit
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
fun CountryItemView(country: Country, onCountryPressed: (Country) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable { onCountryPressed(country) }
        ) {
            Text(
                modifier = Modifier,
                text = "${country.name}, ${country.country}",
                fontSize = 18.sp
            )
            Text(
                modifier = Modifier,
                text = "${country.coordinate.latitude}, ${country.coordinate.longitude}",
                fontSize = 14.sp
            )
        }
        //val favouriteImage = R.drawable.heart.takeIf { country.isFavourite } ?: R.drawable.heart_outline
        Image(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(R.drawable.database_search),
            contentDescription = null
        )
    }
}

@Preview
@Composable
fun SuccessfulFilterViewPreview() {
    SuccessfulFilterView(
        listOf(
            Country(
                "123",
                name = "Mexico City",
                country = "MX",
                isFavourite = true,
                coordinate = Coordinate(latitude = 34.283333, longitude = 44.549999)
            ),
            Country(
                "123",
                name = "Axutla",
                country = "MX",
                isFavourite = false,
                coordinate = Coordinate(latitude = 34.283333, longitude = 44.549999)
            ),
            Country(
                "123",
                name = "El chinal",
                country = "MX",
                isFavourite = true,
                coordinate = Coordinate(latitude = 34.283333, longitude = 44.549999)
            ),
        )
    ) { _ -> }
}
