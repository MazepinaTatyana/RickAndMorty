package com.example.rickandmorty.di

import android.content.Context
import com.example.rickandmorty.api.ApiService
import com.example.rickandmorty.api.RickAndMortyDataSource
import com.example.rickandmorty.database.RickAndMortyDao
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.repository.RickAndMortyRepository
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideCharacterService() : ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://rickandmortyapi.com/api/")
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterDataSource(api: ApiService, database: RickAndMortyDataBase) = RickAndMortyDataSource(api, database)

    @Singleton
    @Provides
    fun provideCharacterDatabase(@ApplicationContext appContext: Context) = RickAndMortyDataBase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(database: RickAndMortyDataBase) =  database.rickAndMortyDao()

    fun provideRepository(api: ApiService, dao: RickAndMortyDao) = RickAndMortyRepository(api, dao)

}