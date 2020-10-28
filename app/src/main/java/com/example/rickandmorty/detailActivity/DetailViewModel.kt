package com.example.rickandmorty.detailActivity

import androidx.lifecycle.ViewModel

class DetailViewModel(val repository: DetailRepository) : ViewModel() {

    fun getCharacterById(id : Int) = repository.getCharacterById(id)
}