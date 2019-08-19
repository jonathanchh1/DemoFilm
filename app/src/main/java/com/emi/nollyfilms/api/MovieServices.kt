package com.emi.nollyfilms.api

import com.emi.nollyfilms.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface  MovieServices{

    @GET("movie/{sort_by}")
    fun getPopularMovies(@Path("sort_by") sort : String?, @Query("page") page : Int?,
                         @Query("api_key") apiKey : String?) : Observable<MovieResponse>
    @GET("movie/{id}/videos")
    fun getTrailers(@Path("id") movieId : String?,  @Query("api_key") apiKey : String?) : Observable<TrailersResponse>

    @GET("movie/{id}/reviews" )
    fun getReviews(@Path("id") reviewId : String?, @Query("api_key") apiKey : String?) : Observable<ReviewsResponse>

}

