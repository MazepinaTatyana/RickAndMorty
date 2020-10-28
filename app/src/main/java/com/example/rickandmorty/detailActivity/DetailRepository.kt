package com.example.rickandmorty.detailActivity

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.Result

class DetailRepository(application: Application) {

    val dataBase = RickAndMortyDataBase.getInstance(application)

    fun getCharacterById(id : Int) : LiveData<Result> {
        return dataBase.rickAndMortyDao().getCharacterById(id)
    }
}