package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

import javax.inject.Inject
import javax.inject.Provider

/**
 *
 * This is the App ViewModels Factory
 *
 * @property mProviderMap is the map of ViewModel-ViewModel Provider for all the
 * app's ViewModels
 *
 *
 * */
class ViewModelFactory @Inject
constructor(private val mProviderMap: Map<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    /**
     *
     * @return a factory of a given ViewModel class from the Provider map
     *
     * */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return (mProviderMap[modelClass] ?: error("")).get() as T
    }
}
