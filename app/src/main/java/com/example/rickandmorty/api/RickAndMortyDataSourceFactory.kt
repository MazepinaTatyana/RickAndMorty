package com.example.rickandmorty.api

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.Result
import javax.inject.Inject

class RickAndMortyDataSourceFactory @Inject constructor (
    private val api: ApiService,
    private val dataBase: RickAndMortyDataBase
) : DataSource.Factory<Int, Result>() {

    val dataSourceLiveData = MutableLiveData<RickAndMortyDataSource>()

    override fun create(): DataSource<Int, Result> {
        val rickAndMortyDataSource = RickAndMortyDataSource(api, dataBase)
        dataSourceLiveData.postValue(rickAndMortyDataSource)
        return rickAndMortyDataSource
    }
}