package com.emi.nollyfilms.cache

import com.emi.nollyfilms.model.MovieResponse
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class DataSource constructor(private var memoryData : MemoryDataSource,
                             private var network: NetworkDataSource){



    fun getDataFromMemory() : Observable<MovieResponse> {
        return memoryData.fetchData()
    }

    fun getNetwork(sort : String?, page : Int?) : Observable<MovieResponse>{
        return network.fetchData(sort, page).doOnNext {
             data ->
              memoryData.cacheInMemory(data)
        }
        }


    }

