package com.example.rickandmorty.detailActivity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailViewModelFactory (private val detailRepository: DetailRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                return DetailViewModel(detailRepository) as T
            } else {
                throw IllegalArgumentException()
            }
        }
    }