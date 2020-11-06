package com.example.rickandmorty.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.example.rickandmorty.api.ApiService
import com.example.rickandmorty.database.RickAndMortyDao
import com.example.rickandmorty.pojo.ExampleResponse
import com.example.rickandmorty.pojo.Result
import io.reactivex.Observable
import javax.inject.Inject


class RickAndMortyRepository @Inject constructor(
    private val api: ApiService,
    private val rickAndMortyDao: RickAndMortyDao
) : Repository {

    override fun getCharacters(page: Int) : Observable<ExampleResponse> {
        return api.getAllInfo(page)
    }

    override fun getCharacters(): DataSource.Factory<Int, Result> {
       return rickAndMortyDao.getInfoAboutRandM()
    }

    override fun getCharacterById(id : Int) : LiveData<Result> {

        return rickAndMortyDao.getCharacterById(id)
    }

}

