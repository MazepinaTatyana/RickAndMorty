package com.example.rickandmorty.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.rickandmorty.api.ApiFactory
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.Result
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelRickAndMorty(application: Application) : AndroidViewModel(application) {
    val db = RickAndMortyDataBase.getInstance(application)
    var info: LiveData<List<Result>>
    lateinit var compositeDisposable: CompositeDisposable

    init {
        info = db.rickAndMortyDao().getInfoAboutRandM()
    }
    
    fun loadData() {
        compositeDisposable = CompositeDisposable()
        val disposable = ApiFactory.apiService.getAllInfo()
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.rickAndMortyDao().deleteAllInfo()
                db.rickAndMortyDao().insertInfoAboutRandM(it)
            }, {

            })
        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}