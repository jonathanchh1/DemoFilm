package com.emi.nollyfilms.vm


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.emi.nollyfilms.api.MovieFinder

import com.emi.nollyfilms.model.Reviews
import com.emi.nollyfilms.model.Trailers
import com.emi.nollyfilms.repo.DetailRepository

import javax.inject.Inject

class DetailViewModel @Inject constructor(finder : MovieFinder) : ViewModel(){

    private var detailRepo: DetailRepository = DetailRepository(finder)
     var streamTrailers : LiveData<MutableList<Trailers>>
     var streamReviews : LiveData<MutableList<Reviews>>


    init {
        streamTrailers = detailRepo.streamTrailers
        streamReviews = detailRepo.streamReview

    }
    

    fun onRequestReviewData(id : String?){
        return detailRepo.onFetchReviews(id)
    }

    fun onRequestTrailerData(id : String?){
       return detailRepo.onFetchedTrailers(id)

    }
    override fun onCleared() {
        super.onCleared()
        detailRepo.clearingCompose()
    }

}