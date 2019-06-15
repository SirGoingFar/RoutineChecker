package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.eemf.sirgoingfar.database.Routine

class RoutineListViewModel(private val mApplication: Application, private val lifecycleOwner: LifecycleOwner) : BaseViewModel(mApplication) {

    private val mRoutineListMutableLiveData: MutableLiveData<List<Routine>> = MutableLiveData()

    fun getRoutineListObserver(): LiveData<List<Routine>> {
        return mRoutineListMutableLiveData
    }

    private fun setRoutineList(routineList: List<Routine>?) {
        setRequestState(STATE_LOADED)
        mRoutineListMutableLiveData.postValue(routineList)
    }

    fun getAllRoutines() {
        setRequestState(STATE_LOADING)
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.getAllRoutine()?.observe(lifecycleOwner, Observer {
                setRoutineList(it)
            })
        }
    }

    fun addRoutine(routine: Routine) {
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.addRoutine(routine)
        }
    }

    fun editRoutine(routine: Routine){
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.
        }
    }

}