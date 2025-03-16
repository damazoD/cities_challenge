package com.damazo.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damazo.data.model.CityEntity

@Dao
interface CityDao {

    @Query("SELECT * FROM city ORDER BY name, country")
    suspend fun getAllCities(): List<CityEntity>

    @Query("SELECT * FROM city WHERE name LIKE :prefix || '%' OR country LIKE :prefix || '%' ORDER BY name, country")
    suspend fun searchBy(prefix: String): List<CityEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(cities: List<CityEntity>)

    @Query("UPDATE city SET isFavourite=:isFavourite WHERE id = :id")
    suspend fun updateFavouriteCity(id: Long, isFavourite: Boolean)

}