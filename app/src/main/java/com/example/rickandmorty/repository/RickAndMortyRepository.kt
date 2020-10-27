package com.example.rickandmorty.repository

import androidx.lifecycle.LiveData
import com.example.rickandmorty.pojo.Result

interface RickAndMortyRepository {

    fun getCharacterById(id : Int) : LiveData<Result>

    fun clearErrors()

    fun loadData(page : Int)

    fun disposeDisposable()
}