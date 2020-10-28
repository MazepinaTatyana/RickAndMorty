package com.example.rickandmorty.detailActivity

import androidx.lifecycle.ViewModel
import com.example.rickandmorty.repository.RickAndMortyRepository

class DetailViewModel(val repo: RickAndMortyRepository) : ViewModel() {

    fun getCharacterById(id : Int) = repo.getCharacterById(id)
}