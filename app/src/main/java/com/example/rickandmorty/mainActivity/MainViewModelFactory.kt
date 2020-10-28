package com.example.rickandmorty.mainActivity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.repository.RickAndMortyRepository


class MainViewModelFactory(
    private val mainRepo: RickAndMortyRepository,
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mainRepo, app) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}