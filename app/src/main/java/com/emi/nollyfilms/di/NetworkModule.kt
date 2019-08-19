package com.emi.nollyfilms.di

import com.emi.nollyfilms.api.MovieFinder.Companion.baseUrl
import com.emi.nollyfilms.api.MovieServices
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object NetworkModule{

    @JvmStatic
    @Singleton
    @Provides
    fun gsonClient() : Gson = GsonBuilder()
        .setLenient()
        .create()

    @JvmStatic
    @Singleton
    @Provides
    fun httpClient() : OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.HEADERS})
        .build()


    @JvmStatic
    @Singleton
    @Provides
    fun providesServices(retrofit: Retrofit) : MovieServices{
        return retrofit.create(MovieServices::class.java)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gsonClient()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(httpClient())
        .build()

}