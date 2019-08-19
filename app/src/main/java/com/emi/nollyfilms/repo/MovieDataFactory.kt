package com.emi.nollyfilms.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.emi.nollyfilms.api.MovieFinder
import com.emi.nollyfilms.api.MovieFinder.Companion.popular
import com.emi.nollyfilms.db.MovieDao
import com.emi.nollyfilms.model.Movies
import io.reactivex.disposables.CompositeDisposable


class MovieDataFactory(private val movieDao: MovieDao,
                       private val composite : CompositeDisposable,  private val movieFinder: MovieFinder
) : DataSource.Factory<Int, Movies>() {

    private lateinit var dataSource : MovieDataSources
    private var sort = ""
    var sourceLiveData = MutableLiveData<DataSource<Int, Movies>>()
    override fun create(): DataSource<Int, Movies> {
        dataSource = MovieDataSources(onQuery(), movieDao, composite, movieFinder)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun onQuery() : String{
        if(sort == ""){
            return popular
        }
        return sort
    }

    fun retryNetWork(){
        dataSource.retryAllFailed()
    }

    fun isWaiting() : LiveData<Boolean>{
        return dataSource.isLoading
    }

    fun source() = sourceLiveData.value

    fun sortChoice(choice : String?){
        this.sort = choice!!
    }
}