package com.damazo.data.mapper

import com.damazo.data.model.CityEntity
import com.damazo.data.model.CityRemote
import com.damazo.data.model.CoordinatesEntity
import com.damazo.data.model.CoordinatesRemote
import org.junit.Assert.*
import org.junit.Test

class CityMapperTest {

    private val mapper = CityMapper()

    @Test
    fun `should return a model with all fields when the entry model also has them`() {
        val cityEntity = CityEntity(
            id = 123,
            name = "Mexico",
            country = "MX",
            coordinates = CoordinatesEntity(12.234, 23.988),
            isFavourite = true
        )

        val result = mapper.mapToDomainModel(cityEntity)

        assertEquals(123L, result.id)
        assertEquals("Mexico", result.name)
        assertEquals("MX", result.country)
        assertEquals(true, result.isFavourite)
        assertNotNull(result.coordinates)
        assertEquals(12.234, result.coordinates?.longitude)
        assertEquals(23.988, result.coordinates?.latitude)
    }

    @Test
    fun `should return a model with null coordinates when the input model does not have them either`() {
        val cityEntity = CityEntity(
            id = 123,
            name = "Mexico",
            country = "MX",
            coordinates = null,
            isFavourite = true
        )

        val result = mapper.mapToDomainModel(cityEntity)

        assertEquals(123L, result.id)
        assertEquals("Mexico", result.name)
        assertEquals("MX", result.country)
        assertEquals(true, result.isFavourite)
        assertNull(result.coordinates)
    }

    @Test
    fun `should return model with all fields when the entry model also has them and favourite false`() {
        val cityRemote = CityRemote(
            id = 123,
            name = "Mexico",
            country = "MX",
            coordinates = CoordinatesRemote(12.234, 23.988)
        )

        val result = mapper.mapToEntityModel(cityRemote)

        assertEquals(123L, result?.id)
        assertEquals("Mexico", result?.name)
        assertEquals("MX", result?.country)
        assertEquals(false, result?.isFavourite)
        assertNotNull(result?.coordinates)
        assertEquals(12.234, result?.coordinates?.longitude)
        assertEquals(23.988, result?.coordinates?.latitude)
    }

    @Test
    fun `should return null when the model has a null id`() {
        val cityRemote = CityRemote(
            id = null,
            name = "Mexico",
            country = "MX",
            coordinates = CoordinatesRemote(12.234, 23.988)
        )

        val result = mapper.mapToEntityModel(cityRemote)

        assertNull(result)
    }

    @Test
    fun `should return null when the model has a null name`() {
        val cityRemote = CityRemote(
            id = 123,
            name = null,
            country = "MX",
            coordinates = CoordinatesRemote(12.234, 23.988)
        )

        val result = mapper.mapToEntityModel(cityRemote)

        assertNull(result)
    }

    @Test
    fun `should return a domain model with null coordinates when the input model does not have them either`() {
        val cityRemote = CityRemote(
            id = 123,
            name = "Mexico",
            country = "MX",
            coordinates = null
        )

        val result = mapper.mapToEntityModel(cityRemote)

        assertEquals(123L, result?.id)
        assertEquals("Mexico", result?.name)
        assertEquals("MX", result?.country)
        assertEquals(false, result?.isFavourite)
        assertNull(result?.coordinates)
    }
}