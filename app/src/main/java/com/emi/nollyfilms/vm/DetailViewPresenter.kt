package com.emi.nollyfilms.vm

import android.content.Context
import android.content.Intent
import android.util.Log
import com.emi.nollyfilms.R
import com.emi.nollyfilms.model.Reviews
import com.emi.nollyfilms.model.Trailers
import com.emi.nollyfilms.ui.VideoActivity

class DetailViewPresenter constructor(private var context: Context, var trailers: Trailers,  var review : Reviews) {

    fun author() = review.author
    fun comments() = review.content
    fun videoThumb() = trailers.key



    init {
        Log.d(DetailViewPresenter::class.java.simpleName, "${trailers.key}, there's a key")
    }

    fun onPlay(){
         val intent = Intent(context, VideoActivity::class.java)
         intent.putExtra(context.getString(R.string.videourl), trailers.key)
         context.startActivity(intent)
    }
}