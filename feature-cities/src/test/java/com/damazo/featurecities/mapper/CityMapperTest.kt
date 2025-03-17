package com.damazo.featurecities.mapper

import com.damazo.domain.model.City
import com.damazo.domain.model.Coordinates
import org.junit.Assert.*
import org.junit.Test

class CityMapperTest{
    private val mapper = CityMapper()

    @Test
    fun `should return a model with all fields when the entry model also has them`() {
        val city = City(
            id = 123,
            name = "Mexico",
            country = "MX",
            coordinates = Coordinates(12.234, 23.988),
            isFavourite = true
        )

        val result = mapper.mapToUiModel(city)

        assertEquals(123L, result.id)
        assertEquals("Mexico, MX", result.displayName)
        assertEquals(true, result.isFavourite)
        assertNotNull(result.coordinates)
        assertEquals(12.234, result.coordinates?.longitude)
        assertEquals(23.988, result.coordinates?.latitude)
    }

    @Test
    fun `should return a model with null coordinates when the input model does not have them either`() {
        val city = City(
            id = 123,
            name = "Mexico",
            country = "MX",
            coordinates = null,
            isFavourite = true
        )

        val result = mapper.mapToUiModel(city)

        assertEquals(123L, result.id)
        assertEquals("Mexico, MX", result.displayName)
        assertEquals(true, result.isFavourite)
        assertNull(result.coordinates)
    }
}