package com.damazo.featurecities.mapper

import com.damazo.featurecities.model.Coordinates
import com.damazo.domain.model.City as City
import com.damazo.featurecities.model.City as CityUi
import javax.inject.Inject

class CityMapper @Inject constructor() {

    fun mapToUiModel(cityModel: City) = CityUi(
        id = cityModel.id,
        displayName = "${cityModel.name}, ${cityModel.country}",
        coordinates = if (cityModel.coordinates != null)
            Coordinates(
                longitude = cityModel.coordinates!!.longitude,
                latitude = cityModel.coordinates!!.latitude
            ) else null,
        isFavourite = cityModel.isFavourite,
    )
}