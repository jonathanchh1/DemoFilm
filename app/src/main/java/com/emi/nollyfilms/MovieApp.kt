package com.emi.nollyfilms

import android.app.Application
import com.emi.nollyfilms.di.ComponentProvider
import com.emi.nollyfilms.di.DaggerMovieComponent
import com.emi.nollyfilms.di.MovieComponent

class MovieApp : Application(), ComponentProvider{


    override val appComponent: MovieComponent by lazy{
             DaggerMovieComponent
                 .builder()
                 .applicationContext(this)
                 .build()
    }

}