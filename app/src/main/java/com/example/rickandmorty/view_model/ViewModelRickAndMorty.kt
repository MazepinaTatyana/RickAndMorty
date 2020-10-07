package com.example.rickandmorty.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmorty.api.ApiFactory
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.Info
import com.example.rickandmorty.pojo.Result
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ViewModelRickAndMorty(application: Application) : AndroidViewModel(application) {
    val db = RickAndMortyDataBase.getInstance(application)
    var result: LiveData<List<Result>>
    var compositeDisposable = CompositeDisposable()
    var errors : MutableLiveData<Throwable>
    var info = Info()

    init {
        result = db.rickAndMortyDao().getInfoAboutRandM()
        errors = MutableLiveData()

    }

    fun getCharacterById(id : Int) : LiveData<Result> {
      return db.rickAndMortyDao().getCharacterById(id)
    }

    fun clearErrors() {
        errors.value = null
    }

    fun loadData(page : Int) {
        compositeDisposable = CompositeDisposable()
        val disposable = ApiFactory.apiService.getAllInfo(page)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it != null) {
                    it.results?.let { it1 -> db.rickAndMortyDao().insertInfoAboutRandM(it1) }
                }

                info = it.info!!

            }, {
                errors.postValue(it)

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