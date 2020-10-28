package com.example.rickandmorty.mainActivity

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.Info
import com.example.rickandmorty.repository.RickAndMortyRepository
import io.reactivex.schedulers.Schedulers

class MainViewModel(val repo: RickAndMortyRepository, application: Application) : ViewModel() {

    val dataBase = RickAndMortyDataBase.getInstance(application)
    var errors = MutableLiveData<Throwable>()
    var result = dataBase.rickAndMortyDao().getInfoAboutRandM()
    var info = Info()

    fun loadData(page: Int) {
        val disposable = repo.getCharacters(page)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null) {
                    it.results?.let { it1 -> dataBase.rickAndMortyDao().insertInfoAboutRandM(it1) }
                }
                info = it.info!!
            }, {
                errors.postValue(it)

            })
    }
    fun clearErrors() {
        errors.value = null
    }
}