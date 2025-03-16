package com.damazo.data.di

import android.content.Context
import androidx.room.Room
import com.damazo.data.BuildConfig
import com.damazo.data.datasource.local.AppDatabase
import com.damazo.data.datasource.local.CityDao
import com.damazo.data.datasource.local.DATABASE_NAME
import com.damazo.data.datasource.remote.CitiesGistService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @ExperimentalSerializationApi
    fun provideRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        val jsonFormat = Json { ignoreUnknownKeys = true }
        val convertFactory = jsonFormat.asConverterFactory(contentType)

        return Retrofit.Builder()
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(convertFactory)
            .baseUrl(BuildConfig.API_BASE_URL)
            .build()
    }

    @Provides
    fun provideCitiesGistService(retrofit: Retrofit): CitiesGistService {
        return retrofit.create(CitiesGistService::class.java)
    }

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideCityDao(database: AppDatabase): CityDao {
        return database.cityDao()
    }
}