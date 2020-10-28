package com.example.rickandmorty.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class MainViewModel(val repository: MainRepository) : ViewModel() {

    fun loadData(page: Int) = repository.loadData(page)

    val errors = repository.errors
    val info = repository.info
    val result = repository.result

    fun clearErrors() {
        errors.value = null
    }
}