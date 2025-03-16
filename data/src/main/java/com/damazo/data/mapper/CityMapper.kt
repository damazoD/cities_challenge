package com.damazo.data.mapper

import com.damazo.data.model.CityEntity
import com.damazo.data.model.CityRemote
import com.damazo.data.model.CoordinatesEntity
import com.damazo.domain.model.City
import com.damazo.domain.model.Coordinates
import javax.inject.Inject

class CityMapper @Inject constructor() {

    fun mapToDomainModel(cityEntity: CityEntity) = City(
        id = cityEntity.id,
        name = cityEntity.name,
        country = cityEntity.country,
        coordinates = if (cityEntity.coordinates != null)
            Coordinates(
                cityEntity.coordinates.latitude,
                cityEntity.coordinates.longitude
            ) else null,
        isFavourite = cityEntity.isFavourite,
    )

    fun mapToEntityModel(cityRemote: CityRemote): CityEntity? {
        return if (cityRemote.id != null && cityRemote.name != null) {
            val latitude = cityRemote.coordinates?.latitude
            val longitude = cityRemote.coordinates?.latitude
            CityEntity(
                id = cityRemote.id,
                name = cityRemote.name,
                country = cityRemote.country.orEmpty(),
                coordinates = if (latitude != null && longitude != null)
                    CoordinatesEntity(longitude, latitude)
                else null,
                isFavourite = false,
            )
        } else null
    }
}