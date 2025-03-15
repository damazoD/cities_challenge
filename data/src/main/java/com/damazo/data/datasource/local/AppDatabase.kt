package com.damazo.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.damazo.data.model.CityEntity

const val DATABASE_NAME = "db-cities-challenge"

@Database(entities = [CityEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao
}