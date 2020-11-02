package com.example.rickandmorty.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.Result
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RickAndMortyDataSource @Inject constructor(
    private val api: ApiService,
    private val dataBase: RickAndMortyDataBase
) : PageKeyedDataSource<Int, Result>() {

    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null
    private val compositeDisposable = CompositeDisposable()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Result>
    ) {
        updateState(State.LOADING)
        compositeDisposable.add(
            api.getAllInfo(1)
                .subscribe({
                    updateState(State.DONE)
                    it.results?.let { it1 ->
                        callback.onResult(
                            it1,
                            null,
                            2
                        )

                    }
                }, {
                    updateState(State.ERROR)
                    setRetry(Action { loadInitial(params, callback) })

                })
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            api.getAllInfo(params.key)
                .subscribe({
                    updateState(State.DONE)
                    it.results?.let { it1 ->
                        callback.onResult(
                            it1,
                            params.key + 1
                        )
                        dataBase.rickAndMortyDao().insertInfoAboutRandM(it1)
                    }
                }, {
                    updateState(State.ERROR)
                    setRetry(Action { loadAfter(params, callback) })
                })
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Result>) {
    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(
                retryCompletable!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                    }, {
                        Log.i("retryError", it.message.toString())
                    })
            )
        }
    }

    private fun setRetry(action: Action?) {
        if (action == null) retryCompletable = null
        else retryCompletable = Completable.fromAction(action)
    }
}