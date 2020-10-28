//package com.example.rickandmorty.mainActivity
//
//import android.app.Application
//import androidx.lifecycle.MutableLiveData
//import com.example.rickandmorty.api.ApiFactory
//import com.example.rickandmorty.database.RickAndMortyDataBase
//import com.example.rickandmorty.pojo.Info
//import io.reactivex.schedulers.Schedulers
//
//class MainRepository(application: Application) {
//
//    val dataBase = RickAndMortyDataBase.getInstance(application)
//    var errors = MutableLiveData<Throwable>()
//    var result = dataBase.rickAndMortyDao().getInfoAboutRandM()
//    var info = Info()
//
//    fun loadData(page: Int) {
//        val disposable = ApiFactory.apiService.getAllInfo(page)
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                if (it != null) {
//                    it.results?.let { it1 -> dataBase.rickAndMortyDao().insertInfoAboutRandM(it1) }
//                }
//                info = it.info!!
//            }, {
//                errors.postValue(it)
//
//            })
//    }
//}