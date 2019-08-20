package com.emi.nollyfilms.cache

import android.content.Context
import com.emi.nollyfilms.R
import com.emi.nollyfilms.model.MovieResponse
import io.reactivex.Observable

class MemoryDataSource constructor(private var context: Context){

     private var mResponse = MovieResponse()
     private var memCache  = HashMap<Any, Any>()
     fun fetchData() : Observable<MovieResponse> {
        return Observable.create { emitter ->
            emitter.onNext(mResponse)
            emitter.onComplete()
        }
    }

     fun cacheInMemory(response: MovieResponse) : MovieResponse{
         this.mResponse = response
         memCache[context.getString(R.string.movieCache)] = response
         val cache = memCache[context.getString(R.string.movieCache)] as MovieResponse
         return cache
    }
}