package com.emi.nollyfilms.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.emi.nollyfilms.api.MovieFinder
import com.emi.nollyfilms.cache.DataSource
import com.emi.nollyfilms.cache.MemoryDataSource
import com.emi.nollyfilms.cache.NetworkDataSource
import com.emi.nollyfilms.db.MovieDao
import com.emi.nollyfilms.db.MovieRoomDatabase
import com.emi.nollyfilms.model.Movies
import com.emi.nollyfilms.repo.MovieDataFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.operators.observable.ObservableMergeWithMaybe
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val context: Context, private val movieFinder: MovieFinder): ViewModel() {

    private val pageSize = 1
   private  var sourceFactory: MovieDataFactory
    private var db: MovieRoomDatabase = MovieRoomDatabase.getDatabase(context, viewModelScope)
    private var movieDao: MovieDao
    private val compositeDisposable = CompositeDisposable()
     var factoryList : Observable<PagedList<Movies>>
    private var loadingDb : Observable<PagedList<Movies>>?=null

    init {
        movieDao = db.movieDao()
        sourceFactory = MovieDataFactory(movieDao, compositeDisposable, movieFinder, context)
        factoryList = factoryList()
        Observable.fromArray(factoryList)
            .switchMap { s ->
                s.distinctUntilChanged()
            }.toList()
            .subscribe()
    }

    fun factoryList() : Observable<PagedList<Movies>>{
      return  RxPagedListBuilder(sourceFactory, config())
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            .cache()
            .cacheWithInitialCapacity(100)

    }

    fun databaseLoader() : Observable<PagedList<Movies>>?{
        loadingDb = RxPagedListBuilder(movieDao.getAllMovies(), 5)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            return Observable.merge(factoryList, loadingDb)
    }

    private fun config(): PagedList.Config {
        val config = PagedList.Config.Builder()
            .setPageSize(100)
            .setPrefetchDistance(2)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(true)
            .build()
        return config
    }


    fun onFetch(sort: String?){
         sourceFactory.sortChoice(sort)
         sourceFactory.source()?.invalidate()
    }

    fun refresh(swipeRefreshLayout: SwipeRefreshLayout){
        sourceFactory.source()?.invalidate()
        swipeRefreshLayout.isRefreshing = false

    }

    fun isLoading() : LiveData<Boolean>{
        return sourceFactory.isWaiting()
    }

    fun retry(){
     sourceFactory.retryNetWork()
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}