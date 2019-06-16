package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.eemf.sirgoingfar.database.Routine

class RoutineListActivityViewModel(mApplication: Application, private val lifecycleOwner: LifecycleOwner) : BaseViewModel(mApplication) {

    private val mRoutineMutableLiveData: MutableLiveData<Routine> = MutableLiveData()
    private val mRoutineListMutableLiveData: MutableLiveData<List<Routine>> = MutableLiveData()

    fun getRoutineObserver(): LiveData<Routine> {
        return mRoutineMutableLiveData
    }

    fun getRoutineListObserver(): LiveData<List<Routine>> {
        return mRoutineListMutableLiveData
    }

    private fun setRoutine(routine: Routine?) {
        setRequestState(STATE_LOADED)
        mRoutineMutableLiveData.postValue(routine)
    }

    private fun setRoutineList(routineList: List<Routine>?) {
        setRequestState(STATE_LOADED)
        mRoutineListMutableLiveData.postValue(routineList)
    }

    fun getRoutineById(routineId: Int) {
        setRequestState(STATE_LOADING)
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.getRoutineById(routineId)?.observe(lifecycleOwner, Observer {
                setRoutine(it)
            })
        }
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

    fun editRoutine(routine: Routine) {
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.updateRoutine(routine)
        }
    }

}