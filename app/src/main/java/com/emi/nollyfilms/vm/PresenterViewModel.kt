package com.emi.nollyfilms.vm

import android.content.Context
import android.content.Intent
import android.view.View
import com.emi.nollyfilms.R
import com.emi.nollyfilms.model.Movies
import com.emi.nollyfilms.ui.DetailActivity
import com.emi.nollyfilms.ui.MovieActivity
import kotlinx.android.synthetic.main.content_main.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import javax.inject.Inject

class PresenterViewModel @Inject constructor( val context: Context,  var movies: Movies){


    fun title() = movies.original_title
    fun thumbnail() = movies.thumbnail
    fun desc() = movies.overview
    fun popular() = movies.popularity?.times(2.4)


    fun onShare() {
        val intent = Intent(Intent.ACTION_SEND)
         intent.type = "text/plain"
         intent.putExtra(Intent.EXTRA_SUBJECT, movies.thumbnail)
         intent.putExtra(Intent.EXTRA_TEXT, movies.overview)
         context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)))
    }

    fun onClick(v : View){
        val intent = Intent(context, DetailActivity::class.java)
         intent.putExtra(context.getString(R.string.movies), movies)
        context.startActivity(intent)
    }
}