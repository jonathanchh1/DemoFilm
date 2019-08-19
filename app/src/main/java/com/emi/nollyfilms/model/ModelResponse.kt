package com.emi.nollyfilms.model

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class MovieResponse constructor(@SerializedName("results") var results : ArrayList<Movies>?=null,
                                     @SerializedName("page") var page : Int?=null,
                                     @SerializedName("total_results") var totalResults : Int?=null,
                                     @SerializedName("total_pages") var totalPages : Int?=null)

data class TrailersResponse constructor(@SerializedName("results") var trailers: ArrayList<Trailers>?=null)

data class ReviewsResponse constructor(@SerializedName("results") var reviews : ArrayList<Reviews>?=null)
