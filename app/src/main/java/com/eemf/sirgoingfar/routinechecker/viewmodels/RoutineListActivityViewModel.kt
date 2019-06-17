package com.eemf.sirgoingfar.routinechecker.viewmodels

import android.app.Application
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.eemf.sirgoingfar.database.Routine

/**
 *
 * This is the ViewModel for the RoutineList page
 *
 * @property mApplication is the Application instance of the Caller or the LifeCycle owner
 * that's instantiating the ViewModel
 *
 * @property lifecycleOwner is the instance of the ViewModel lifecycle owner
 *
 * @property mRoutineListMutableLiveData is the livedata object of the list of
 * Routines
 *
 * @property mRoutineMutableLiveData is the livedata object of a requested by Id Routine
 *
 * */
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
        setUiState(STATE_LOADED)
        mRoutineMutableLiveData.postValue(routine)
    }

    private fun setRoutineList(routineList: List<Routine>?) {
        setUiState(STATE_LOADED)
        mRoutineListMutableLiveData.postValue(routineList)
    }

    fun getRoutineById(routineId: Int) {
        setUiState(STATE_LOADING)
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.getRoutineById(routineId)?.observe(lifecycleOwner, Observer {
                setRoutine(it)
            })
        }
    }

    fun getAllRoutines() {
        setUiState(STATE_LOADING)
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

    fun deleteRoutine(routine: Routine) {
        mExecutors?.diskIO()?.execute {
            mDb?.dao?.deleteRoutine(routine)
        }
    }

}