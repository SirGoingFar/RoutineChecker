package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.eemf.sirgoingfar.core.utils.App
import com.eemf.sirgoingfar.core.utils.AppExecutors
import com.eemf.sirgoingfar.database.AppDatabase

open class BaseViewModel(mApplication: Application) : AndroidViewModel(mApplication) {

    protected var mDb: AppDatabase? = AppDatabase.getInstance(mApplication)
    protected var mExecutors: AppExecutors? = App.getsExecutors()
    private val requestState = MutableLiveData<Int>()

    protected fun setRequestState(state: Int) {
        requestState.postValue(state)
    }

    fun getRequestStateObserver(): LiveData<Int> {
        return requestState
    }

    companion object {
        val STATE_LOADING = 0
        val STATE_LOADED = 1
    }
}

