package com.emi.nollyfilms.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.emi.nollyfilms.api.MovieFinder
import com.emi.nollyfilms.db.MovieDao
import com.emi.nollyfilms.db.MovieRoomDatabase
import com.emi.nollyfilms.model.Movies
import com.emi.nollyfilms.repo.MovieDataFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val context: Context, private val movieFinder: MovieFinder): ViewModel() {

    private val pageSize = 5
    private  var sourceFactory: MovieDataFactory
    private var db: MovieRoomDatabase = MovieRoomDatabase.getDatabase(context, viewModelScope)
    private var movieDao: MovieDao
    private val compositeDisposable = CompositeDisposable()
    var factoryList : Observable<PagedList<Movies>>


    init {
        movieDao = db.movieDao()
        sourceFactory = MovieDataFactory(movieDao, compositeDisposable, movieFinder)
        factoryList = RxPagedListBuilder(sourceFactory, config())
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
             .cache()
            .cacheWithInitialCapacity(100)

        Observable.fromArray(factoryList)
            .switchMap { s ->
                s.distinctUntilChanged()
            }.toList()
            .doAfterSuccess { Log.d(HomeViewModel::class.java.simpleName, "$it new list") }
            .subscribe()
    }

    private fun config(): PagedList.Config {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setMaxSize(40)
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


    companion object{
        const val movieData = "movies"
        const val videoData = "videos"
        const val reviewData = "reviews"

       fun displayError(){}

        fun errorHandling(error : Throwable?) {
            return when(error){
                is SocketTimeoutException ->  displayError()
                is HttpException -> displayError()
                is UnknownHostException -> displayError()
                else -> displayError()
            }
        }
    }

}