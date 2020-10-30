package com.example.rickandmorty.mainActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.repository.RickAndMortyRepository

class MainViewModelFactory(
    private val mainRepo: RickAndMortyRepository,
    private val dataBase: RickAndMortyDataBase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainRepo, dataBase) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}