package com.emi.nollyfilms.di

import com.emi.nollyfilms.model.Movies
import com.emi.nollyfilms.model.Reviews
import com.emi.nollyfilms.model.Trailers
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor

import javax.inject.Singleton


@Module
object ModelModule {

    @JvmStatic
    @Provides
    @Singleton
    fun providesMovies() : Movies{
        return Movies()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun providesReviews() : Reviews{
        return Reviews()
    }


    @JvmStatic
    @Provides
    @Singleton
    fun provideExecutor() : Executor{
        return Executor{it.run()}
    }

    @JvmStatic
    @Provides
    @Singleton
    fun providesTrailers() : Trailers{
        return Trailers()
    }

}