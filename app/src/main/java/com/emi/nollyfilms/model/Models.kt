package com.emi.nollyfilms.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity(tableName = "table_movies")
data class Movies(@PrimaryKey @Expose @SerializedName("id")
                  @ColumnInfo(name = "id") var id : Int = 0,
                  @SerializedName("poster_path")
                  @ColumnInfo(name = "thumbnail") var thumbnail : String?=null,
                  @Expose @SerializedName("overview")
                  @ColumnInfo(name = "overview") val overview : String?=null,
                  @Expose @SerializedName("release_date")
                  @ColumnInfo(name = "release")var release : String?=null,
                  @Expose @SerializedName("original_title")
                  @ColumnInfo(name = "orginal_title")
                  var original_title : String?=null,
                  @Expose @SerializedName("title")
                  @ColumnInfo(name = "title")
                  var title : String?=null,
                  @Expose @SerializedName("backdrop_path")
                  @ColumnInfo(name = "detail_thumbnail")
                  var detail_thumbnail : String?=null,
                  @Expose @SerializedName("popularity")
                  @ColumnInfo(name = "popularity")
                  var popularity : Double?=null,
                  @Expose @SerializedName("vote_count")
                  @ColumnInfo(name = "vote_count")
                  var vote_count : Int?=null,
                  @Expose @SerializedName("vote_average") @ColumnInfo(name = "vote_average")
                  var vote_average : Double?=null,
                  @Expose @SerializedName("video")
                  var video : Boolean?=null,
                  @Expose @SerializedName("adult")
                  @ColumnInfo(name = "adult") var Isadult :Boolean?=null) : Parcelable

@Parcelize
data class Trailers(@PrimaryKey @Expose @SerializedName("id") var id : String = "",
                    @Expose @SerializedName("key") var key : String?=null,
                    @Expose @SerializedName("name") var name : String?=null,
                    @Expose @SerializedName("site") var site : String?=null,
                    @Expose @SerializedName("size") var size : String?=null) : Parcelable

@Parcelize
data class Reviews(@PrimaryKey
                   @Expose @SerializedName("id") var id : String = "",
                   @Expose @SerializedName("author") var author : String?=null,
                   @Expose @SerializedName("content") var content : String?=null,
                   @Expose @SerializedName("url") var url : String?=null) : Parcelable

@Parcelize
data class Crew(@PrimaryKey @Expose @SerializedName("id") var id : Int = 0,
                @Expose @SerializedName("department") var department : String?=null,
                @Expose @SerializedName("job") var job : String?=null,
                @Expose @SerializedName("name") var name : String?=null) : Parcelable


@Parcelize
data class Cast(@PrimaryKey @Expose @SerializedName("id")var id : Int = 0,
                @Expose @SerializedName("character") var character : String?=null,
                @Expose @SerializedName("credit_id") var credit_id : String?=null,
                @Expose @SerializedName("name") var name : String?=null) : Parcelable



@Parcelize
data class Recommendation(@PrimaryKey var id : Int = 0,
                          @Expose @SerializedName("backdrop_path") var backthumbnail : String?=null,
                          @Expose @SerializedName("original_title") var originalTitle : String?=null,
                          @Expose @SerializedName("overview") var overview : String?=null,
                          @Expose @SerializedName("poster_path") var thumbnail : String?=null,
                          @Expose @SerializedName("release_date") var releaseDate : String?=null,
                          @Expose @SerializedName("title") var title : String?=null,
                          @Expose @SerializedName("vote_average") var voteAverage : String?=null,
                          @Expose @SerializedName("popularity") var popularity : String?=null,
                          @Expose @SerializedName("adult") var isAdult : String?=null) : Parcelable


@Parcelize
data class Similar(@PrimaryKey var id : Int = 0,
                   @Expose @SerializedName("backdrop_path") var backthumbnail : String?=null,
                   @Expose @SerializedName("original_title") var originalTitle : String?=null,
                   @Expose @SerializedName("overview") var overview : String?=null,
                   @Expose @SerializedName("poster_path") var thumbnail : String?=null,
                   @Expose @SerializedName("release_date") var releaseDate : String?=null,
                   @Expose @SerializedName("title") var title : String?=null,
                   @Expose @SerializedName("vote_average") var voteAverage : String?=null,
                   @Expose @SerializedName("popularity") var popularity : String?=null,
                   @Expose @SerializedName("adult") var isAdult : String?=null) : Parcelable









