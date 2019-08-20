package com.emi.nollyfilms.cache

import com.emi.nollyfilms.api.MovieFinder
import com.emi.nollyfilms.model.MovieResponse
import io.reactivex.Observable
import javax.inject.Inject


class NetworkDataSource @Inject constructor(private var movieFinder: MovieFinder){




    fun fetchData(sort : String?, page : Int?)  =
       movieFinder.findMovies(sort, page).doOnNext {
              response ->
            Observable.create<MovieResponse> { emitter ->
                emitter.onNext(response)
                emitter.onComplete()
            }
        }
        }



