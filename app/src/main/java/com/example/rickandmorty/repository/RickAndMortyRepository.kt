package com.example.rickandmorty.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.rickandmorty.api.ApiService
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.ExampleResponse
import com.example.rickandmorty.pojo.Result
import io.reactivex.Observable

class RickAndMortyRepository(
    private val api: ApiService,
    private val dataBase: RickAndMortyDataBase
) {
    fun getCharacters(page: Int) : Observable<ExampleResponse> {
        return api.getAllInfo(page)
    }

    fun getCharacterById(id : Int) : LiveData<Result> {
        return dataBase.rickAndMortyDao().getCharacterById(id)
    }
}