package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.eemf.sirgoingfar.database.RoutineOccurrence

class RoutineDetailOccurrenceListViewModel(mApplication: Application, private val lifecycleOwner: LifecycleOwner) : BaseViewModel(mApplication) {

    private val mRoutineOccurrenceListMutableLiveData: MutableLiveData<List<RoutineOccurrence>> = MutableLiveData()

    fun getRoutineOccurrenceListObserver(): LiveData<List<RoutineOccurrence>> {
        return mRoutineOccurrenceListMutableLiveData
    }

    private fun setRoutineOccurrenceList(routineList: List<RoutineOccurrence>?) {
        setRequestState(STATE_LOADED)
        mRoutineOccurrenceListMutableLiveData.postValue(routineList)
    }

    fun getAllRoutineOccurrences(routineId: Int) {
        setRequestState(STATE_LOADING)
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.getAllRoutineOccurrences(routineId)?.observe(lifecycleOwner, Observer {
                setRoutineOccurrenceList(it)
            })
        }
    }
}