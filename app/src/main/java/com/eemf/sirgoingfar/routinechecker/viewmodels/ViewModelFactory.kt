package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject
constructor(private val mProviderMap: Map<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return (mProviderMap[modelClass] ?: error("")).get() as T
    }
}
