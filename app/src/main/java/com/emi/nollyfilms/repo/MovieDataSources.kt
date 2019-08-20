package com.emi.nollyfilms.repo

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.emi.nollyfilms.api.MovieFinder
import com.emi.nollyfilms.cache.DataSource
import com.emi.nollyfilms.cache.MemoryDataSource
import com.emi.nollyfilms.cache.NetworkDataSource
import com.emi.nollyfilms.db.MovieDao
import com.emi.nollyfilms.model.Movies
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers


class MovieDataSources constructor(private var sort : String?, private val movieDao: MovieDao,
                                   private val compositeDisposable: CompositeDisposable,
                                   private var movieFinder: MovieFinder, private var context: Context) : PageKeyedDataSource<Int, Movies>(){

    private var disposable = Disposables.empty()
    private var retry: (() -> Any)? = null
    var isLoading = MutableLiveData<Boolean>(false)
    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movies>) {
        val numberOfItems = params.requestedLoadSize
        return createWebServer(1, 1, numberOfItems, callback, null, null )
    }


    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {

        val page = params.key
        val numberOfNumbers = params.requestedLoadSize
        return createWebServer( page, page + 1, numberOfNumbers, null, callback, params)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        val page = params.key
        val numberOfItems = params.requestedLoadSize
       return  createWebServer(page, page-1, numberOfItems, null, callback, null)
    }



    private fun createWebServer(requestPage : Int?, adjacentPage : Int,
                                 requestedLoadSize : Int, initalCallBack : LoadInitialCallback<Int, Movies>?,
                                 callback : LoadCallback<Int, Movies>?, params: LoadParams<Int>?) {

        val memoryCache = MemoryDataSource(context)
        val networkCache = NetworkDataSource(movieFinder)
        val dataSource = DataSource(memoryCache, networkCache)
        compositeDisposable.add(dataSource.getNetwork(sort, requestPage?.times(requestedLoadSize))
            .observeOn(Schedulers.computation()).subscribe())

        compositeDisposable.add(dataSource.getDataFromMemory()
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                response.results?.sortByDescending { it.popularity }
                initalCallBack?.onResult(response.results!!, null, adjacentPage)
                callback?.onResult(response.results!!, adjacentPage)
                response.results?.map {
                    insertDb(it) }
                retry = null
                Log.d(MovieDataSources::class.java.simpleName, "$sort movie sort")
                isLoading.postValue(false)

            },
                { error ->
                    retry = {
                        loadAfter(params!!, callback!!)
                    }
                    isLoading.postValue(true)
                    Log.d(MovieDataSources::class.java.simpleName, "${error.printStackTrace()}")
                }
            )
        )

    }

    private fun insertDb(movie : Movies){
        compositeDisposable.add(Observable.fromCallable { movieDao.insert(movie)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(MovieDataSources::class.java.simpleName, "$it its ids")
            },
                {error -> Log.d(MovieDataSources::class.java.simpleName, "${error.message}")}))

    }

    override fun invalidate() {
        super.invalidate()
        compositeDisposable.clear()
        disposable.dispose()
    }

}