package com.example.rickandmorty.main



import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.example.rickandmorty.api.ApiService
import com.example.rickandmorty.api.RickAndMortyDataSource
import com.example.rickandmorty.api.RickAndMortyDataSourceFactory
import com.example.rickandmorty.api.State
import com.example.rickandmorty.database.RickAndMortyDataBase
import com.example.rickandmorty.pojo.Info
import com.example.rickandmorty.pojo.Result
import com.example.rickandmorty.repository.RickAndMortyRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainViewModel @ViewModelInject constructor(
    val repo: RickAndMortyRepository,
    private val dataBase: RickAndMortyDataBase,
    private val api: ApiService
    ) : ViewModel() {

    private val pageSize = 5


    var charactersList: LiveData<PagedList<Result>>
    private val compositeDisposable = CompositeDisposable()
    private val rickAndMortyDataSourceFactory: RickAndMortyDataSourceFactory
    var datasource = RickAndMortyDataSource(api, dataBase)
    var result = dataBase.rickAndMortyDao().getInfoAboutRandM().toLiveData(5)
    var info = Info()
    var errors = datasource.errors

    init {
        rickAndMortyDataSourceFactory = RickAndMortyDataSourceFactory(api, dataBase)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()

        charactersList = LivePagedListBuilder(rickAndMortyDataSourceFactory, config).build()
    }

    fun getState() : LiveData<State> = Transformations.switchMap(
        rickAndMortyDataSourceFactory.dataSourceLiveData,
        RickAndMortyDataSource::state
    )

    fun retry() {
        rickAndMortyDataSourceFactory.dataSourceLiveData.value?.retry()
    }

    fun listIsEmpty(): Boolean {
        return charactersList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    val characterPagedList: LiveData<PagedList<Result>> =
        dataBase.rickAndMortyDao().getInfoAboutRandM().toLiveData(10)


    fun getCharacters(page: Int) {
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