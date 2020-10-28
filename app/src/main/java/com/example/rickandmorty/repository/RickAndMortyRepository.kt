package com.example.rickandmorty.repository

import androidx.lifecycle.LiveData
import com.example.rickandmorty.api.ApiService
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.ExampleResponse
import com.example.rickandmorty.pojo.Result
import io.reactivex.Observable

class RickAndMortyRepository(
    private val api: ApiService,
    private val dataBase: RickAndMortyDataBase
) : Repository {

    override fun getCharacters(page: Int) : Observable<ExampleResponse> {
        return api.getAllInfo(page)
    }

    override fun getCharacterById(id : Int) : LiveData<Result> {
        return dataBase.rickAndMortyDao().getCharacterById(id)
    }
}