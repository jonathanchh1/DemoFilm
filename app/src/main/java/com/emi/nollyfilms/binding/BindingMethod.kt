package com.emi.nollyfilms.binding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.emi.nollyfilms.BR
import com.emi.nollyfilms.R
import com.emi.nollyfilms.api.MovieFinder.Companion.backdrop
import com.emi.nollyfilms.api.MovieFinder.Companion.jpg
import com.emi.nollyfilms.api.MovieFinder.Companion.thumbnails
import com.emi.nollyfilms.api.MovieFinder.Companion.videothumbnail
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso

object BindingMethod{

    @JvmStatic
    @BindingAdapter("srcCompat")
    fun imageloader(view : ImageView, url : String?){
        Picasso.with(view.context).load(thumbnails+url)
            .placeholder(R.drawable.placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("srcBackDrop")
    fun imageBackDroploader(view : ImageView, url : String?){
        Picasso.with(view.context).load(backdrop+url).
            placeholder(R.drawable.placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("srcvideo")
    fun videoimageLoader(view : ImageView, url : String?){
        Picasso.with(view.context).load(videothumbnail+"${url}"+jpg).
            placeholder(R.drawable.placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("srcDrawable")
    fun imagedrawabler(view : ImageView, @DrawableRes id : Int){
        view.setImageResource(id)
    }
}