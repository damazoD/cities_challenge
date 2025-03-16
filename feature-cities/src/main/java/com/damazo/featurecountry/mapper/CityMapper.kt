package com.damazo.featurecountry.mapper

import com.damazo.featurecountry.model.Coordinates
import com.damazo.domain.model.City as City
import com.damazo.featurecountry.model.City as CityUi
import javax.inject.Inject

class CityMapper @Inject constructor() {

    fun mapToUiModel(cityModel: City) = CityUi(
        id = cityModel.id,
        displayName = "${cityModel.name}, ${cityModel.country}",
        displayCoordinates = "${cityModel.coordinates?.latitude ?: "0"}, ${cityModel.coordinates?.longitude ?: "0"}",
        coordinates = if (cityModel.coordinates != null)
            Coordinates(
                cityModel.coordinates!!.latitude,
                cityModel.coordinates!!.longitude
            ) else null,
        isFavourite = cityModel.isFavourite,
    )
}