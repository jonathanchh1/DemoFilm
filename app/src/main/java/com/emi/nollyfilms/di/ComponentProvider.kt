package com.emi.nollyfilms.di

import androidx.fragment.app.Fragment


interface ComponentProvider{
    val appComponent : MovieComponent
}
val Fragment.injector get() = (requireActivity().application as ComponentProvider).appComponent


