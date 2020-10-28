package com.example.rickandmorty.detailActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.repository.RickAndMortyRepository

class DetailViewModelFactory (private val detailRepository: RickAndMortyRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(detailRepository) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }