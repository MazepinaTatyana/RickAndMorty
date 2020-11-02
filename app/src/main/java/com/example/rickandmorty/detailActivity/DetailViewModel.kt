package com.example.rickandmorty.detailActivity

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.repository.RickAndMortyRepository

class DetailViewModel @ViewModelInject constructor(val repo: RickAndMortyRepository) : ViewModel() {

    fun getCharacterById(id : Int) = repo.getCharacterById(id)
}