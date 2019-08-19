package com.emi.nollyfilms.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.emi.nollyfilms.api.MovieFinder
import com.emi.nollyfilms.model.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

 class DetailRepository @Inject constructor(private val movieFinder: MovieFinder) : Disposable {

    private var disposable = Disposables.empty()
    private val compositeDisposable = CompositeDisposable()
     var streamReview = MutableLiveData<MutableList<Reviews>>()
     var streamTrailers = MutableLiveData<MutableList<Trailers>>()

    fun onFetchedTrailers(id : String?){
        disposable = movieFinder.findVideos(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                trailers ->
                streamTrailers.value = trailers.trailers?.toMutableList()
                Log.d(DetailRepository::class.java.simpleName, "${trailers.trailers}")
            }, {
                    error -> Log.d(DetailRepository::class.java.simpleName, "${error.printStackTrace()}")
                })
            compositeDisposable.add(disposable)
    }


    fun onFetchReviews(id : String?){
        disposable = movieFinder.findReviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                review -> streamReview.value = review.reviews?.toMutableList()
                Log.d(DetailRepository::class.java.simpleName, "${review.reviews}")

            }, {
                 error -> Log.d(DetailRepository::class.java.simpleName, "${error.printStackTrace()}")
                })
    }

    fun clearingCompose(){
        compositeDisposable.clear()
    }

    override fun isDisposed(): Boolean {
        return disposable.isDisposed
    }
    override fun dispose() {
        return disposable.dispose()
    }
}