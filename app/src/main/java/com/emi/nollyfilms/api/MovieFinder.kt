package com.emi.nollyfilms.api

import com.emi.nollyfilms.BuildConfig
import javax.inject.Inject

class MovieFinder @Inject constructor(private var movieServices: MovieServices){

    fun findMovies(sort : String?, page : Int?) =
        movieServices.getPopularMovies(sort, page, BuildConfig.APIKEY)

    fun findVideos(id : String?) =
        movieServices.getTrailers(id, BuildConfig.APIKEY)

    fun findReviews(id: String?) =
        movieServices.getReviews(id, BuildConfig.APIKEY)



    companion object{
        const val baseUrl = "http://api.themoviedb.org/3/"
        const val popular = "popular"
        const val topRated = "top_rated"
        const val thumbnails = "http://image.tmdb.org/t/p/w185"
        const val backdrop = "https://image.tmdb.org/t/p/original"
        const val videothumbnail = "http://img.youtube.com/vi/"
        const val jpg = "/0.jpg"
    }

}