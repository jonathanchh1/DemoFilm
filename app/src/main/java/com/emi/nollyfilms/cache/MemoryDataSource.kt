package com.emi.nollyfilms.cache

import com.emi.nollyfilms.model.MovieResponse
import io.reactivex.Observable

 class MemoryDataSource{

     private var mResponse = MovieResponse()
    fun fetchData() : Observable<MovieResponse> {
        return Observable.create { emitter ->
            emitter.onNext(mResponse.copy())
            emitter.onComplete()
        }
    }

     fun cacheInMemory(response: MovieResponse){
        this.mResponse = response.copy()
    }
}