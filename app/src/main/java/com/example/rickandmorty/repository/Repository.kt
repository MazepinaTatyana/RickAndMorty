package com.example.rickandmorty.repository

import androidx.lifecycle.LiveData
import com.example.rickandmorty.pojo.ExampleResponse
import com.example.rickandmorty.pojo.Result
import io.reactivex.Observable

interface Repository {

    fun getCharacters(page: Int) : Observable<ExampleResponse>

    fun getCharacterById(id : Int) : LiveData<Result>
}