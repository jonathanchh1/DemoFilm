package com.emi.nollyfilms.di

import android.content.Context
import android.renderscript.Sampler
import androidx.fragment.app.Fragment
import com.emi.nollyfilms.vm.DetailViewModel
import com.emi.nollyfilms.vm.HomeViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, ModelModule::class])
interface MovieComponent{

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun applicationContext(application : Context) : Builder
        fun build() : MovieComponent
    }

    fun context() : Context
    fun moviesViewModelFactory() : ViewModelFactory<HomeViewModel>
    fun detailViewModelFactory() : ViewModelFactory<DetailViewModel>

}