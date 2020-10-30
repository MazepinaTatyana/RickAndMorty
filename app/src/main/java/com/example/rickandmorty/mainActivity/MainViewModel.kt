package com.example.rickandmorty.mainActivity



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


class MainViewModel(
    val repo: RickAndMortyRepository,
    private val dataBase: RickAndMortyDataBase
    ) : ViewModel() {

    private val page = 1
    private val pageSize = 5
    private val api = ApiService.getService()

    var charactersList: LiveData<PagedList<Result>>
    private val compositeDisposable = CompositeDisposable()
    private val rickAndMortyDataSourceFactory: RickAndMortyDataSourceFactory

    var errors = MutableLiveData<Throwable>()
    var result = dataBase.rickAndMortyDao().getInfoAboutRandM()
    var info = Info()

    init {
        rickAndMortyDataSourceFactory = RickAndMortyDataSourceFactory(api, compositeDisposable, dataBase)
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